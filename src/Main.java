import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<User> users = new ArrayList<>();
    private static ArrayList<Product> products = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    private static User loggedUser = null;

    public static void main(String[] args) {
        while (true) {
            if (loggedUser == null) {
                showWelcomeMenu();
            } else {
                showMainMenu();
            }
        }
    }

    private static void showWelcomeMenu() {
        System.out.println("\n=== VendiX ===");
        System.out.println("1. Login");
        System.out.println("2. Cadastrar");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": login(); break;
            case "2": registerUser(); break;
            case "0": System.exit(0); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void login() {
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
                if (user.getPassword().equals(password)) {
                    loggedUser = user;
                    System.out.println("✅ Login realizado com sucesso! Bem-vindo(a), " + user.getName() + ".");
                } else {
                    System.out.println("❌ Email e/ou senha incorretos.");
                }
                return;
            }
        }

        System.out.println("❌ Usuário não encontrado.");
    }

    private static void registerUser() {
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

    private static void showMainMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Produtos");
        System.out.println("2. Clientes (em breve)");
        System.out.println("3. Vendas (em breve)");
        System.out.println("4. Configurações de conta");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": showProductMenu(); break;
            case "4": showAccountMenu(); break;
            case "0": loggedUser = null; System.out.println("Logout realizado com sucesso."); break;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void showProductMenu() {
        System.out.println("\n=== Produtos ===");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Produtos");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": registerProduct(); break;
            case "2": listProducts(); break;
            case "0": return;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void registerProduct() {
        System.out.println("\n=== Cadastro de Produto ===");

        System.out.print("Nome do produto: ");
        String name = scanner.nextLine().trim();

        System.out.print("Preço: ");
        String priceStr = scanner.nextLine().trim();

        System.out.print("Quantidade: ");
        String quantityStr = scanner.nextLine().trim();

        if (name.isEmpty() || priceStr.isEmpty() || quantityStr.isEmpty()) {
            System.out.println("❌ Todos os campos são obrigatórios.");
            return;
        }

        double price;
        int quantity;

        try {
            price = Double.parseDouble(priceStr);
            quantity = Integer.parseInt(quantityStr);
        } catch (NumberFormatException e) {
            System.out.println("❌ Preço e quantidade devem ser números válidos.");
            return;
        }

        if (price < 0) {
            System.out.println("❌ O preço não pode ser negativo.");
            return;
        }

        if (quantity < 0) {
            System.out.println("❌ A quantidade não pode ser negativa.");
            return;
        }

        products.add(new Product(name, price, quantity));
        System.out.println("✅ Produto cadastrado com sucesso!");
    }

    private static void listProducts() {
        System.out.println("\n=== Lista de Produtos ===");
        if (products.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Product product : products) {
            System.out.println("Nome: " + product.getName() + " | Preço: R$" + product.getPrice() + " | Quantidade: " + product.getQuantity());
        }
    }

    private static void showAccountMenu() {
        System.out.println("\n=== Configurações da Conta ===");
        System.out.println("1. Editar Perfil");
        System.out.println("2. Remover Conta");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": editUser(); break;
            case "2": removeAccount(); break;
            case "0": return;
            default: System.out.println("Opção inválida.");
        }
    }

    private static void editUser() {
        System.out.println("\n=== Editar Perfil ===");

        System.out.print("Novo nome (aperte Enter para manter): ");
        String newName = scanner.nextLine().trim();

        System.out.print("Novo e-mail (aperte Enter para manter): ");
        String newEmail = scanner.nextLine().trim();

        System.out.print("Nova senha (aperte Enter para manter): ");
        String newPassword = scanner.nextLine().trim();

        if (!newEmail.isEmpty()) {
            for (User user : users) {
                if (!user.equals(loggedUser) && user.getEmail().equalsIgnoreCase(newEmail)) {
                    System.out.println("Este e-mail já está sendo usado por outro usuário.");
                    return;
                }
            }
        }

        if (!newName.isEmpty()) {
            loggedUser.setName(newName);
        }

        if (!newEmail.isEmpty()) {
            loggedUser.setEmail(newEmail);
        }

        if (!newPassword.isEmpty()) {
            if (newPassword.length() < 6) {
                System.out.println("A senha deve ter no mínimo 6 caracteres.");
                return;
            }
            loggedUser.setPassword(newPassword);
        }

        System.out.println("Perfil atualizado com sucesso.");
    }

    private static void removeAccount() {
        System.out.println("\n=== Remover Conta ===");
        System.out.print("Tem certeza que deseja remover sua conta? (s/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("s")) {
            users.remove(loggedUser);
            loggedUser = null;
            System.out.println("✅ Conta removida com sucesso.");
        } else {
            System.out.println("Remoção cancelada.");
        }
    }
}
