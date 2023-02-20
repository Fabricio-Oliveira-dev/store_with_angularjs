app.controller("livroController", function($scope, $http, $location, $routeParams) {

	if ($routeParams.id != null && $routeParams.id != undefined
		&& $routeParams.id != '') {// se estiver editando o livro

		// entra pra editar
		$http.get("livro/buscarlivro/" + $routeParams.id).success(function(response) {
			$scope.livro = response;

			document.getElementById("imagemLivro").src = $scope.livro.foto;
			
			//Lista todos os fornecedores do livro selecionado-------------
				$http.get("fornecedor/listartodos").success(function(response) {
					$scope.fornecedoresList = response;
					setTimeout(function() {
						$("#selectLivros").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.livro.fornecedor.id));
					}, 1000);
	
				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});
			//Fim da lista de todos os fornecedores do livro selecionado----
			
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});

	} else { // novo livro
		$scope.livro = {};
	}

	$scope.editarLivro = function(id) {
		$location.path('livroedit/' + id);
	};
	
	// salva ou edita o livro
	$scope.salvarLivro = function() {
		$scope.livro.foto = document.getElementById("imagemLivro").getAttribute("src");

		$http.post("livro/salvar", $scope.livro).success(function(response) {
			$scope.livro = {};
			document.getElementById("imagemLivro").src = '';
			sucesso("Gravado com sucesso!");
		}).error(function(response) {
			erro("Error" + response);
		});
	};
	
	// lista todos os livros
	$scope.listarLivros = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("livro/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;

			///----Inicio total página
			$http.get("livro/totalPagina/").success(function(response) {
				$scope.totalPagina = response;
			}).error(function(response) {
				erro("Error" + response);
			});
			///----Fim total página


		}).error(function(response) {
			erro("Error" + response);
		});
	};

	// função de paginação próximo
	$scope.proximo = function() {
		if (new Number($scope.numeroPagina) < new Number($scope.totalPagina)) {
			$scope.listarLivros(new Number($scope.numeroPagina + 1));
		}
	};
	
	// função de paginação anterior
	$scope.anterior = function() {
		if (new Number($scope.numeroPagina) > 1) {
			$scope.listarLivros(new Number($scope.numeroPagina - 1));
		}
	};
	
	// remove o livro
	$scope.removerLivro = function(codLivro) {
		$http.delete("livro/deletar/" + codLivro).success(function(response) {
			$scope.listarLivros($scope.numeroPagina);
			sucesso("Removido com sucesso!");
		}).error(function(status) {
			erro("Error" + status);
		});
	};
	
	// lista todos os fornecedores do livro
	$scope.listarFornecedores = function() {
		$http.get("fornecedor/listartodos").success(function(response) {
			$scope.fornecedoresList = response;
		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});
	};

});