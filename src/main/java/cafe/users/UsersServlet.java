package cafe.users;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class UsersServlet extends HttpServlet {
    private final UserRepository userRepository;

    public UsersServlet(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("userList", userRepository.findAll());
        req.getRequestDispatcher("/WEB-INF/views/users/userList.jsp").forward(req, resp);
    }

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 간단한 회원가입 처리 로직 (예: 유효성 검사, 사용자 저장 등)
        if (password.equals(confirmPassword)) {
            // 회원가입 성공 로직
            User user = new User(username, email, password);
            userRepository.save(user);
            response.sendRedirect("/users");
        } else {
            // 회원가입 실패 로직
            request.setAttribute("errorMessage", "Passwords do not match");
            request.setAttribute("username", username);
            request.setAttribute("email", email);
            request.getRequestDispatcher("/WEB-INF/views/users.jsp").forward(request, response);
        }
    }
}
