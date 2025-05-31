package models;

import utils.DataManager;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static ArrayList<User> users = new ArrayList<>();
    public static ArrayList<Product> products = new ArrayList<>();
	public static ArrayList<Client> clients = new ArrayList<>();
	public static ArrayList<Sale> sales = new ArrayList<>();

	public static Scanner scanner = new Scanner(System.in);
    public static User loggedUser = null;

    // necessário para testes
    public Main(Scanner scanner) {
        this.scanner = scanner;
    }

    public static void main(String[] args) {
        users = DataManager.loadFromFile("users.dat");
        products = DataManager.loadFromFile("products.dat");
        clients = DataManager.loadFromFile("clients.dat");
		sales = DataManager.loadFromFile("sales.dat");

        Main app = new Main(scanner);

        while (true) {
            if (loggedUser == null) {
                app.showWelcomeMenu();
            } else {
                app.showMainMenu();
            }
        }
    }

    public static void showWelcomeMenu() {
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

        DataManager.saveToFile("users.dat", users);
        System.out.println("✅ Usuário cadastrado com sucesso!");
    }

    public static void showMainMenu() {
        System.out.println("\n=== Menu ===");
        System.out.println("1. Produtos");
        System.out.println("2. Clientes");
        System.out.println("3. Vendas");
        System.out.println("4. Configurações de conta");
        System.out.println("0. Sair");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": showProductMenu(); break;
            case "2": showClientMenu(); break;
			case "3": showSalesMenu(); break;
            case "4": showAccountMenu(); break;
            case "0":
                loggedUser = null;
                System.out.println("Logout realizado com sucesso.");
                break;
            default: System.out.println("Opção inválida.");
        }
    }

    public static void showProductMenu() {
        System.out.println("\n=== Produtos ===");
        System.out.println("1. Cadastrar Produto");
        System.out.println("2. Listar Produtos");
		System.out.println("3. Editar Produto");
		System.out.println("4. Remover Produto");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": registerProduct(); break;
            case "2": listProducts(); break;
			case "3": editProduct(); break;
			case "4": removeProduct(); break;
            case "0": return;
            default: System.out.println("Opção inválida.");
        }
    }

    public static void registerProduct() {
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

		DataManager.saveToFile("products.dat", products);
        System.out.println("✅ Produto cadastrado com sucesso!");
    }

    public static void listProducts() {
        System.out.println("\n=== Lista de Produtos ===");
        if (products.isEmpty()) {
            System.out.println("Nenhum produto cadastrado.");
            return;
        }

        for (Product product : products) {
            System.out.println("Nome: " + product.getName() + " | Preço: R$" + product.getPrice() + " | Quantidade: " + product.getQuantity());
        }
    }

	public static void editProduct() {
		System.out.println("\n=== Editar Produto ===");
		System.out.print("Digite o nome do produto a ser editado: ");
		String nameToEdit = scanner.nextLine().trim();

		Product productToEdit = null;

		for (Product product : products) {
			if (product.getName().equalsIgnoreCase(nameToEdit)) {
				productToEdit = product;
				break;
			}
		}

		if (productToEdit == null) {
			System.out.println("❌ Produto não encontrado.");
			return;
		}

		System.out.print("Novo nome (aperte Enter para manter): ");
		String newName = scanner.nextLine().trim();

		System.out.print("Novo preço (aperte Enter para manter): ");
		String newPriceStr = scanner.nextLine().trim();

		System.out.print("Nova quantidade (aperte Enter para manter): ");
		String newQuantityStr = scanner.nextLine().trim();

		if (!newName.isEmpty()) {
			productToEdit.setName(newName);
		}

		if (!newPriceStr.isEmpty()) {
			try {
				double newPrice = Double.parseDouble(newPriceStr);
				if (newPrice < 0) {
					System.out.println("❌ O preço não pode ser negativo.");
					return;
				}
				productToEdit.setPrice(newPrice);
			} catch (NumberFormatException e) {
				System.out.println("❌ Preço inválido.");
				return;
			}
		}

		if (!newQuantityStr.isEmpty()) {
			try {
				int newQuantity = Integer.parseInt(newQuantityStr);
				if (newQuantity < 0) {
					System.out.println("❌ A quantidade não pode ser negativa.");
					return;
				}
				productToEdit.setQuantity(newQuantity);
			} catch (NumberFormatException e) {
				System.out.println("❌ Quantidade inválida.");
				return;
			}
		}

		DataManager.saveToFile("products.dat", products);
		System.out.println("✅ Produto atualizado com sucesso.");
	}

	public static void removeProduct() {
		System.out.println("Digite o nome do produto a ser removido:");
		String name = scanner.nextLine().trim();

		Product productToRemove = null;

		for (Product p : products) {
			if (p.getName().equalsIgnoreCase(name)) {
				productToRemove = p;
				break;
			}
		}

		if (productToRemove == null) {
			System.out.println("Produto não encontrado.");
			return;
		}

		System.out.printf("Tem certeza que deseja remover o produto '%s'? (s/n): ", productToRemove.getName());
		String confirmation = scanner.nextLine().trim().toLowerCase();

		if (confirmation.equals("s")) {
			products.remove(productToRemove);
			System.out.println("Produto removido com sucesso!");
		} else {
			System.out.println("Remoção cancelada.");
		}
	}

    public static void showAccountMenu() {
        System.out.println("\n=== Configurações da Conta ===");
        System.out.println("1. Editar Perfil");
        System.out.println("2. Excluir Conta");
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

    public static void editUser() {
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

        DataManager.saveToFile("users.dat", users);
        System.out.println("Perfil atualizado com sucesso.");
    }

    public static void removeAccount() {
        System.out.println("\n=== Remover Conta ===");
        System.out.print("Tem certeza que deseja remover sua conta? (s/n): ");
        String confirm = scanner.nextLine().trim().toLowerCase();

        if (confirm.equals("s")) {
            users.remove(loggedUser);
            loggedUser = null;
            DataManager.saveToFile("users.dat", users);
            System.out.println("✅ Conta excluída com sucesso.");
        } else {
            System.out.println("Exclusão cancelada.");
        }
    }

	public static void registerClient() {
		System.out.println("\n=== Cadastro de Cliente ===");

		System.out.print("Nome: ");
		String name = scanner.nextLine().trim();

		System.out.print("Email: ");
		String email = scanner.nextLine().trim();

		System.out.print("CPF: ");
		String cpf = scanner.nextLine().trim();

		if (name.isEmpty() || email.isEmpty() || cpf.isEmpty()) {
			System.out.println("Erro: Todos os campos são obrigatórios.");
			return;
		}

		if (!email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$")) {
			System.out.println("Erro: Email inválido.");
			return;
		}

		if (!cpf.matches("\\d{11}")) {
			System.out.println("Erro: CPF deve conter 11 dígitos numéricos.");
			return;
		}

		boolean emailExists = clients.stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(email));
		boolean cpfExists = clients.stream().anyMatch(c -> c.getCpf().equals(cpf));

		if (emailExists) {
			System.out.println("Erro: Este email já está cadastrado.");
			return;
		}

		if (cpfExists) {
			System.out.println("Erro: Este CPF já está cadastrado.");
			return;
		}

		clients.add(new Client(name, email, cpf));

		DataManager.saveToFile("clients.dat", clients);
		System.out.println("Cliente cadastrado com sucesso!");
	}

	public static void listClients() {
		System.out.println("\n=== Lista de Clientes ===");

		if (clients.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
			return;
		}

		for (int i = 0; i < clients.size(); i++) {
			System.out.println((i + 1) + ". " + clients.get(i));
		}
	}

	public static void editClient() {
		System.out.println("\n=== Editar Cliente ===");
		System.out.print("Digite o nome do cliente a ser editado: ");
		String nameToEdit = scanner.nextLine().trim();

		Client clientToEdit = null;

		for (Client client : clients) {
			if (client.getName().equalsIgnoreCase(nameToEdit)) {
				clientToEdit = client;
				break;
			}
		}

		if (clientToEdit == null) {
			System.out.println("❌ Cliente não encontrado.");
			return;
		}

		System.out.print("Novo nome (aperte Enter para manter): ");
		String newName = scanner.nextLine().trim();

		if (!newName.isEmpty()) {
			clientToEdit.setName(newName);
		}

		System.out.print("Novo email (aperte Enter para manter): ");
		String newEmail = scanner.nextLine().trim();

		if (!newEmail.isEmpty() && !newEmail.equals(clientToEdit.getEmail())) {
			if (clients.stream().anyMatch(c -> c.getEmail().equalsIgnoreCase(newEmail))) {
				System.out.println("Email já cadastrado.");
				return;
			}

			clientToEdit.setEmail(newEmail);
		}

		System.out.print("Novo CPF (aperte Enter para manter): ");
		String newCPF = scanner.nextLine();

		if (!newCPF.isEmpty() && !newCPF.equals(clientToEdit.getCpf())) {
			if (clients.stream().anyMatch(c -> c.getCpf().equals(newCPF))) {
				System.out.println("CPF já cadastrado.");
				return;
			}

			clientToEdit.setCpf(newCPF);
		}

		DataManager.saveToFile("clients.dat", clients);
		System.out.println("Cliente atualizado com sucesso.");
	}

	public static void removeClient() {
		System.out.print("\nDigite o nome do cliente que deseja remover: ");
		String name = scanner.nextLine().trim();

		Client clientToRemove = clients.stream()
			.filter(c -> c.getName().equalsIgnoreCase(name))
			.findFirst()
			.orElse(null);

		if (clientToRemove == null) {
			System.out.println("Cliente não encontrado.");
			return;
		}

		System.out.print("Tem certeza que deseja remover " + clientToRemove.getName() + "? (s/n): ");
		String confirm = scanner.nextLine();

		if (confirm.equalsIgnoreCase("s")) {
			clients.remove(clientToRemove);
			System.out.println("Cliente removido com sucesso.");
		} else {
			System.out.println("Remoção cancelada.");
		}
	}

	public static void showClientMenu() {
        System.out.println("\n=== Clientes ===");
        System.out.println("1. Cadastrar Cliente");
        System.out.println("2. Listar Clientes");
		System.out.println("3. Editar Cliente");
		System.out.println("4. Remover Cliente");
        System.out.println("0. Voltar");
        System.out.print("Escolha uma opção: ");

        String option = scanner.nextLine();

        switch (option) {
            case "1": registerClient(); break;
            case "2": listClients(); break;
			case "3": editClient(); break;
			case "4": removeClient(); break;
            case "0": return;
            default: System.out.println("Opção inválida.");
        }
    }

	public static void showSalesMenu() {
		System.out.println("\n=== Vendas ===");
		System.out.println("1. Registrar Venda");
		System.out.println("2. Listar Vendas");
		System.out.println("0. Voltar");
		System.out.print("Escolha uma opção: ");

		String option = scanner.nextLine();

		switch (option) {
			case "1": registerSale(); break;
			case "2": listSales(); break;
			case "0": return;
			default: System.out.println("Opção inválida.");
		}
	}

	public static void registerSale() {
		System.out.println("\n=== Registrar Venda ===");

		if (clients.isEmpty()) {
			System.out.println("Nenhum cliente cadastrado.");
			return;
		}

		if (products.isEmpty()) {
			System.out.println("Nenhum produto cadastrado.");
			return;
		}

		System.out.println("Selecione o cliente:");

		for (int i = 0; i < clients.size(); i++) {
			System.out.println((i + 1) + ". " + clients.get(i).getName());
		}

		System.out.print("Digite o número do cliente: ");
		int clientIndex;

		try {
			clientIndex = Integer.parseInt(scanner.nextLine()) - 1;
			if (clientIndex < 0 || clientIndex >= clients.size()) {
				System.out.println("Cliente inválido.");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida.");
			return;
		}

		Client client = clients.get(clientIndex);

		System.out.println("Selecione o produto:");

		for (int i = 0; i < products.size(); i++) {
			System.out.println((i + 1) + ". " + products.get(i).getName() + " (Estoque: " + products.get(i).getQuantity() + ")");
		}

		System.out.print("Digite o número do produto: ");
		int productIndex;

		try {
			productIndex = Integer.parseInt(scanner.nextLine()) - 1;
			if (productIndex < 0 || productIndex >= products.size()) {
				System.out.println("Produto inválido.");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida.");
			return;
		}

		Product product = products.get(productIndex);

		System.out.print("Quantidade: ");
		int quantity;

		try {
			quantity = Integer.parseInt(scanner.nextLine());

			if (quantity <= 0) {
				System.out.println("Quantidade inválida.");
				return;
			}

			if (quantity > product.getQuantity()) {
				System.out.println("Quantidade excede o estoque disponível.");
				return;
			}
		} catch (NumberFormatException e) {
			System.out.println("Entrada inválida.");
			return;
		}

		product.setQuantity(product.getQuantity() - quantity);

		Sale sale = new Sale(client, product, quantity);
		sales.add(sale);

		DataManager.saveToFile("products.dat", products);
		DataManager.saveToFile("sales.dat", sales);

		System.out.println("✅ Venda registrada com sucesso.");
	}

	public static void listSales() {
		System.out.println("\n=== Lista de Vendas ===");

		if (sales.isEmpty()) {
			System.out.println("Nenhuma venda registrada.");
			return;
		}

		for (int i = 0; i < sales.size(); i++) {
			Sale sale = sales.get(i);

			System.out.printf("%d. Cliente: %s | Produto: %s | Quantidade: %d | Total: R$ %.2f\n",
				(i + 1),
				sale.getClient().getName(),
				sale.getProduct().getName(),
				sale.getQuantity(),
				sale.getProduct().getPrice() * sale.getQuantity()
			);
		}
	}
}
