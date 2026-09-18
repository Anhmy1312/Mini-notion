// 1. Kết nối với các thành phần trên giao diện (DOM)
const API_URL = 'http://localhost:8080/api/documents';
const form = document.querySelector('form');
const docList = document.querySelector('main ul'); // Thẻ <ul> chứa danh sách tài liệu
const submitBtn = document.getElementById('submitBtn'); // Nút submit

let currentSubject = ''; // Biến lưu trạng thái môn học đang được chọn trên Sidebar

// --- 🔒 SECURITY CHECK ---
// Retrieve the JWT token saved in localStorage during login
const token = localStorage.getItem('jwt_token');

// If there is no token, redirect to the login page immediately
if (!token) {
    alert("You are not logged in! Redirecting to login page...");
    window.location.href = 'login.html';
}

// Helper function to attach the JWT Token to HTTP headers
function getAuthHeaders() {
    return {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
    };
}
// -------------------------

// --- 👤 DISPLAY USERNAME ---
function displayUserGreeting() {
    const username = localStorage.getItem('username');
    const greetingBox = document.getElementById('userGreeting');

    if (greetingBox && username) {
        greetingBox.innerText = `👋 Chào mừng, ${username}!`;
    }
}
// Call the function immediately to show the name
displayUserGreeting();


// 2. Hàm tải toàn bộ danh sách tài liệu từ Java Backend khi mở trang (Kết hợp lọc, tìm kiếm và cập nhật thanh tiến độ)
async function fetchDocuments(subject) {

    if (subject !== undefined) {
        currentSubject = subject; // Bây giờ nếu không truyền gì, nó sẽ giữ nguyên môn học hiện tại!
    }

    const keyword = document.getElementById('searchInput') ? document.getElementById('searchInput').value.trim() : '';
    let url = API_URL;
    let params = [];

    if (currentSubject) {
        params.push(`subject=${currentSubject}`);
    }
    if (keyword) {
        params.push(`search=${encodeURIComponent(keyword)}`);
    }

    if (params.length > 0) {
        url += `?${params.join('&')}`;
    }

    try {
        const response = await fetch(url, {
            method: 'GET',
            headers: { 'Authorization': `Bearer ${token}` } // Attach Token
        });

        // If the token is invalid or expired, force logout
        if (response.status === 401 || response.status === 403) {
            alert("Your session has expired or is invalid. Please log in again!");
            localStorage.removeItem('jwt_token');
            window.location.href = 'login.html';
            return;
        }

        let documents = await response.json();
        docList.innerHTML = '';

        // --- 🔀 LOGIC SẮP XẾP TÀI LIỆU ---
        const sortOption = document.getElementById('sortSelect').value;
        if (sortOption === 'newest') {
            documents.sort((a, b) => b.id - a.id); // ID giảm dần (mới tạo lên trước)
        } else if (sortOption === 'oldest') {
            documents.sort((a, b) => a.id - b.id); // ID tăng dần (cũ tạo trước)
        } else if (sortOption === 'name_asc') {
            documents.sort((a, b) => a.name.localeCompare(b.name, 'vi', { sensitivity: 'base' })); // Tên A-Z
        } else if (sortOption === 'name_desc') {
            documents.sort((a, b) => b.name.localeCompare(a.name, 'vi', { sensitivity: 'base' })); // Tên Z-A
        }
        // ----------------------------------

        // --- 📊 TÍNH TOÁN VÀ CẬP NHẬT THANH TIẾN ĐỘ ---
        const total = documents.length;
        const completedCount = documents.filter(doc => doc.completed).length;
        const percent = total > 0 ? Math.round((completedCount / total) * 100) : 0;

        document.getElementById('progressText').innerText = `Đã hoàn thành: ${completedCount} / ${total} tài liệu (${percent}%)`;
        document.getElementById('progressBar').style.width = `${percent}%`;
        // ----------------------------------------------

        if (documents.length === 0) {
            docList.innerHTML = '<li style="justify-content: center; color: #888;">Không tìm thấy tài liệu nào phù hợp!</li>';
            return;
        }

        documents.forEach(doc => {
            const li = document.createElement('li');

            // Kiểm tra trạng thái đã học để thêm class gạch ngang và checked cho checkbox
            const isChecked = doc.completed ? 'checked' : '';
            const textStyle = doc.completed ? 'completed-doc' : '';

            li.innerHTML = `
                <div style="display: flex; align-items: center; gap: 10px;">
                    <!-- Checkbox đánh dấu đã học -->
                    <input type="checkbox" ${isChecked} onchange="toggleComplete(${doc.id}, this.checked, \`${doc.name}\`, \`${doc.link}\`, \`${doc.subject}\`)" style="cursor: pointer; width: 18px; height: 18px;">
                    
                    <span class="${textStyle}">
                        <strong>${doc.name}</strong> - Môn: ${doc.subject} - 
                        <a href="${doc.link}" target="_blank">Xem tài liệu</a>
                    </span>
                </div>
                <div>
                    <button onclick="editDocument(${doc.id}, \`${doc.name}\`, \`${doc.link}\`, \`${doc.subject}\`)" style="background-color: #f39c12; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer;">Sửa</button>
                    <button onclick="deleteDocument(${doc.id})" style="background-color: #e74c3c; color: white; border: none; padding: 5px 10px; border-radius: 4px; cursor: pointer; margin-left: 5px;">Xóa</button>
                </div>
            `;
            docList.appendChild(li);
        });
    } catch (error) {
        console.error('Lỗi khi tải dữ liệu:', error);
    }
}

// Gọi hàm khi click vào menu Sidebar
function filterDocuments(subject) {
    fetchDocuments(subject);
}

// Hàm tìm kiếm (giữ nguyên môn học đang lọc)
function searchDocuments() {
    fetchDocuments();
}


// 3. Hàm gọi API xóa tài liệu theo ID
async function deleteDocument(id) {
    if (confirm('Bạn có chắc chắn muốn xóa tài liệu này không?')) {
        try {
            const response = await fetch(`${API_URL}/${id}`, {
                method: 'DELETE',
                headers: getAuthHeaders() // Attach Token
            });
            if (response.ok) fetchDocuments();
            else alert('Xóa tài liệu thất bại!');
        } catch (error) {
            console.error('Lỗi khi xóa:', error);
        }
    }
}

// 4. Hàm đưa dữ liệu lên Form để Sửa
function editDocument(id, name, link, subject) {
    document.getElementById('docId').value = id; // Gắn ID vào trường ẩn
    document.getElementById('docName').value = name;
    document.getElementById('docLink').value = link;
    document.getElementById('subject').value = subject;

    submitBtn.innerText = "Cập nhật tài liệu"; // Đổi chữ trên nút
    submitBtn.style.backgroundColor = "#f39c12"; // Đổi màu nút
}

// 5. Lắng nghe sự kiện bấm nút "Thêm tài liệu" để gửi dữ liệu xuống Backend
form.addEventListener('submit', async function (event) {
    event.preventDefault(); // Chặn hành vi load lại trang mặc định của form

    // Lấy giá trị từ các ô input
    const id = document.getElementById('docId').value;
    const name = document.getElementById('docName').value;
    const link = document.getElementById('docLink').value;
    const subject = document.getElementById('subject').value;

    // Đóng gói dữ liệu thành một object JSON
    const newDocument = { name, link, subject };

    try {

        // Gửi yêu cầu POST lên Java Spring Boot API
        let response;
        if (id) {
            // Nếu có ID -> Gọi API PUT để Sửa
            response = await fetch(`${API_URL}/${id}`, {
                method: 'PUT',
                headers: getAuthHeaders(), // Attach Token
                body: JSON.stringify(newDocument)
            });
        } else {
            // Nếu không có ID -> Gọi API POST để Thêm mới
            response = await fetch(API_URL, {
                method: 'POST',
                headers: getAuthHeaders(), // Attach Token
                body: JSON.stringify(newDocument)
            });
        }

        if (response.ok) {
            // Reset lại form về trạng thái ban đầu
            form.reset();
            document.getElementById('docId').value = "";
            submitBtn.innerText = "Thêm tài liệu";
            submitBtn.style.backgroundColor = "#3498db";
            fetchDocuments(); // Gọi lại hàm tải danh sách để cập nhật dữ liệu mới lên màn hình ngay lập tức
        } else {
            alert('Thao tác thất bại!');
        }
    } catch (error) {
        console.error('Lỗi khi lưu dữ liệu:', error);
    }

});

// 6. Hàm tìm kiếm tài liệu theo từ khóa
// function searchDocuments() {
//     fetchDocuments();
// }

// 7. Hàm xử lý khi bấm vào Checkbox "Đã học"
async function toggleComplete(id, isChecked, name, link, subject) {
    const updatedData = {
        name: name,
        link: link,
        subject: subject,
        completed: isChecked // Cập nhật trạng thái mới
    };

    try {
        const response = await fetch(`${API_URL}/${id}`, {
            method: 'PUT',
            headers: getAuthHeaders(), // Attach Token
            body: JSON.stringify(updatedData)
        });

        if (response.ok) {
            fetchDocuments(); // Tải lại danh sách để cập nhật giao diện (gạch ngang chữ)
        } else {
            alert('Cập nhật trạng thái thất bại!');
        }
    } catch (error) {
        console.error('Lỗi khi cập nhật trạng thái:', error);
    }
}

// 8.  --- 🌙 LOGIC BẬT/TẮT DARK MODE ---
function toggleTheme() {
    const isDark = document.body.classList.toggle('dark-theme');
    const toggleBtn = document.getElementById('themeToggleBtn');

    if (isDark) {
        toggleBtn.innerText = "☀️ Sáng";
        localStorage.setItem('theme', 'dark');
    } else {
        toggleBtn.innerText = "🌙 Tối";
        localStorage.setItem('theme', 'light');
    }
}

// Giữ nguyên chế độ sáng/tối khi người dùng F5
(function initTheme() {
    const savedTheme = localStorage.getItem('theme');
    if (savedTheme === 'dark') {
        document.body.classList.add('dark-theme');
        document.getElementById('themeToggleBtn').innerText = "☀️ Sáng";
    }
})();

// --- 🎯 BỘ LỌC MÔN HỌC CÓ HIỆU ỨNG ACTIVE MENU ---
function filterDocuments(subject, element) {
    if (element) {
        document.querySelectorAll('.menu-item').forEach(item => item.classList.remove('active'));
        element.classList.add('active');
    }
    fetchDocuments(subject);
}

// 9. Logout Function (Optional but recommended)
function logout() {
    localStorage.removeItem('jwt_token');
    localStorage.removeItem('username');
    window.location.href = 'login.html';
}