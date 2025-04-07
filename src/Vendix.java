import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    static class User {
        private String name;
        private String email;
        private String password;

        public User(String name, String email, String password) {
            this.name = name;
            this.email = email;
            this.password = password;
        }

        public String getName() {
            return name;
        }

        public String getEmail() {
            return email;
        }

        public boolean validatePassword(String providedPassword) {
            return this.password.equals(providedPassword);
        }
    }

    private static ArrayList<User> users = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        int option;

        do {
            System.out.println("\n===== MENU =====");
            System.out.println("1. Cadastrar Usuário");
            System.out.println("2. Login");
            System.out.println("0. Sair");
            System.out.print("Escolha uma opção: ");
            
            option = scanner.nextInt();
            scanner.nextLine();

            switch (option) {
                case 1:
                    registerUser();
                    break;
                case 2:
                    login();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema...");
                    break;
                default:
                    System.out.println("Opção inválida.");
            }
        } while (option != 0);
    }

    public static void registerUser() {
        System.out.println("\n=== Cadastro de Usuário ===");

        System.out.print("Nome: ");
        String name = scanner.nextLine().trim();

        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha (mínimo 6 caracteres): ");
        String password = scanner.nextLine();

        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Todos os campos são obrigatórios.");
            return;
        }

        if (password.length() < 6) {
            System.out.println("❌ A senha deve ter no mínimo 6 caracteres.");
            return;
        }

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                System.out.println("❌ E-mail já cadastrado.");
                return;
            }
        }

        users.add(new User(name, email, password));
        
        System.out.println("✅ Usuário cadastrado com sucesso!");
    }

    public static void login() {
        System.out.println("\n=== Login ===");

        System.out.print("E-mail: ");
        String email = scanner.nextLine().trim();

        System.out.print("Senha: ");
        String password = scanner.nextLine();

        if (email.isEmpty() || password.isEmpty()) {
            System.out.println("❌ Preencha todos os campos.");
            return;
        }

        for (User user : users) {
            if (user.getEmail().equalsIgnoreCase(email)) {
                if (user.validatePassword(password)) {
                    System.out.println("✅ Login realizado com sucesso! Bem-vindo(a), " + user.getName() + ".");
                } else {
                    System.out.println("❌ Senha incorreta.");
                }
                return;
            }
        }

        System.out.println("❌ E-mail não cadastrado.");
    }
}
