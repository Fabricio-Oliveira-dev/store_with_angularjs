var permissao = false;
// configuração do módulo
var app = angular.module('loja', ['ngRoute', 'ngResource', 'ngAnimate']);


// configurando rotas
app.config(function($routeProvider, $provide, $httpProvider, $locationProvider) {

	$routeProvider.when("/clientelist", {
		controller: "clienteController",
		templateUrl: "cliente/list.html"
	})// listar

		.when("/clienteedit/:id", {
			controller: "clienteController",
			templateUrl: "cliente/cadastro.html"
		})// editar

		.when("/cliente/cadastro", {
			controller: "clienteController",
			templateUrl: "cliente/cadastro.html"
		})// novo

	//Fornecedor-----------------------------------------
	$routeProvider.when("/fornecedorlist", {
		controller: "fornecedorController",
		templateUrl: "fornecedor/list.html"
	})// listar

		.when("/fornecedoredit/:id", {
			controller: "fornecedorController",
			templateUrl: "fornecedor/cadastro.html"
		})// editar

		.when("/fornecedor/cadastro", {
			controller: "fornecedorController",
			templateUrl: "fornecedor/cadastro.html"
		})// novo
		
		//Livro-----------------------------------------
	$routeProvider.when("/livrolist", {
		controller: "livroController",
		templateUrl: "livro/list.html"
	})// listar

		.when("/livroedit/:id", {
			controller: "livroController",
			templateUrl: "fornecedor/cadastro.html"
		})// editar

		.when("/livro/cadastro", {
			controller: "livroController",
			templateUrl: "livro/cadastro.html"
		})// novo


		.otherwise({
			redirectTo: "/"
		});

});

// configurações do controller de fornecedores
app.controller("fornecedorController", function($scope, $http, $location, $routeParams) {

	if ($routeParams.id != null && $routeParams.id != undefined
		&& $routeParams.id != '') {// se estiver editando o fornecedor

		// entra pra editar
		$http.get("fornecedor/buscarfornecedor/" + $routeParams.id).success(function(response) {
			$scope.fornecedor = response;

			document.getElementById("imagemCliente").src = $scope.cliente.foto;
			//------------------ Inicia o carregamento de estados e cidades do fornecedor em edição
			setTimeout(function() {
				$("#selectEstados").prop('selectedIndex', (new Number($scope.fornecedor.estados.id) + 1));

				$http.get("cidades/listar/" + $scope.fornecedor.estados.id).success(function(response) {
					$scope.cidades = response;
					setTimeout(function() {
						$("#selectCidades").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.fornecedor.cidades.id));
					}, 1000);

				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});

			}, 1000);
			//-------------------- Fim do carregamento de estados e cidades do fornecedor

		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});

	} else { // novo fornecedor
		$scope.fornecedor = {};
	}

	$scope.editarFornecedor = function(id) {
		$location.path('clienteedit/' + id);
	};

	// salva ou edita o Fornecedor
	$scope.salvarFornecedor = function() {
		$scope.fornecedor.foto = document.getElementById("imagemFornecedor").getAttribute("src");

		$http.post("fornecedor/salvar", $scope.fornecedor).success(function(response) {
			$scope.fornecedor = {};
			document.getElementById("imagemFornecedor").src = '';
			sucesso("Gravado com sucesso!");
		}).error(function(response) {
			erro("Error" + response);
		});
	};

	// lista todos os fornecedores
	$scope.listarFornecedor = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("fornecedor/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;

			///----Inicio total página
			$http.get("fornecedor/totalPagina/").success(function(response) {
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
			$scope.listarFornecedor(new Number($scope.numeroPagina + 1));
		}
	}
	
	// função de paginação anterior
	$scope.anterior = function() {
		if (new Number($scope.numeroPagina) > 1) {
			$scope.listarFornecedor(new Number($scope.numeroPagina - 1));
		}
	}
	
	// remove o fornecedor
	$scope.removerFornecedor = function(codForn) {
		$http.delete("fornecedor/deletar/" + codForn).success(function(response) {
			$scope.listarFornecedor($scope.numeroPagina);
			sucesso("Removido com sucesso!");
		}).error(function(status) {
			erro("Error" + status);
		});
	};
	
	// carrega as cidades de acordo com o Estado passado de parâmetro
	$scope.carregarCidades = function(estado) {
		if (identific_nav() != 'chrome') {// executa se for diferente do chrome
			$http.get("cidades/listar/" + estado.id).success(function(response) {
				$scope.cidades = response;
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
		}
	};
	
	// carrega os Estados  no combo
	$scope.carregarEstados = function() {
		$scope.dataEstados = [{}];
		$http.get("estados/listar").success(function(response) {
			$scope.dataEstados = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};

});


// configurações do controller de clientes
app.controller("clienteController", function($scope, $http, $location, $routeParams) {

	if ($routeParams.id != null && $routeParams.id != undefined
		&& $routeParams.id != '') {// se estiver editando o cliente

		// entra pra editar
		$http.get("cliente/buscarcliente/" + $routeParams.id).success(function(response) {
			$scope.cliente = response;

			document.getElementById("imagemCliente").src = $scope.cliente.foto;
			//------------------ carrega estados e cidades do cliente em edição
			setTimeout(function() {
				$("#selectEstados").prop('selectedIndex', (new Number($scope.cliente.estados.id) + 1));

				$http.get("cidades/listar/" + $scope.cliente.estados.id).success(function(response) {
					$scope.cidades = response;
					setTimeout(function() {
						$("#selectCidades").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.cliente.cidades.id));
					}, 1000);

				}).error(function(data, status, headers, config) {
					erro("Error: " + status);
				});

			}, 1000);
			//------------------- fim do carregamento de estados e cidade do cliente

		}).error(function(data, status, headers, config) {
			erro("Error: " + status);
		});

	} else { // novo cliente
		$scope.cliente = {};
	}

	$scope.editarCliente = function(id) {
		$location.path('clienteedit/' + id);
	};
	
	//salva ou edita o cliente
	$scope.salvarCliente = function() {
		$scope.cliente.foto = document.getElementById("imagemCliente").getAttribute("src");

		$http.post("cliente/salvar", $scope.cliente).success(function(response) {
			$scope.cliente = {};
			document.getElementById("imagemCliente").src = '';
			sucesso("Gravado com sucesso!");
		}).error(function(response) {
			erro("Error" + response);
		});
	};
	
	// lista todos os clientes
	$scope.listarClientes = function(numeroPagina) {
		$scope.numeroPagina = numeroPagina;
		$http.get("clientelist/listar/" + numeroPagina).success(function(response) {
			$scope.data = response;

			///----Inicio total página
			$http.get("clientelist/totalPagina/").success(function(response) {
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
			$scope.listarClientes(new Number($scope.numeroPagina + 1));
		}
	}
	
	// função de paginação anterior
	$scope.anterior = function() {
		if (new Number($scope.numeroPagina) > 1) {
			$scope.listarClientes(new Number($scope.numeroPagina - 1));
		}
	}
	
	// remove o cliente
	$scope.removerCliente = function(codCliente) {
		$http.delete("cliente/deletar/" + codCliente).success(function(response) {
			$scope.listarClientes($scope.numeroPagina);
			sucesso("Removido com sucesso!");
		}).error(function(status) {
			erro("Error" + status);
		});
	};
	
	// carrega as cidades de acordo com o Estado passado de parâmetro
	$scope.carregarCidades = function(estado) {
		if (identific_nav() != 'chrome') {// executa se for diferente do chrome
			$http.get("cidades/listar/" + estado.id).success(function(response) {
				$scope.cidades = response;
			}).error(function(data, status, headers, config) {
				erro("Error: " + status);
			});
		}
	};
	
	// carrega os Estados  no combo
	$scope.carregarEstados = function() {
		$scope.dataEstados = [{}];
		$http.get("estados/listar").success(function(response) {
			$scope.dataEstados = response;
		}).error(function(response) {
			erro("Error: " + response);
		});
	};

});

// configurações do controller de livros
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
						$("#selectFornecedor").prop('selectedIndex', buscarKeyJson(response, 'id', $scope.livro.fornecedor.id));
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
	$scope.listarLivro = function(numeroPagina) {
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
	}
	
	// função de paginação anterior
	$scope.anterior = function() {
		if (new Number($scope.numeroPagina) > 1) {
			$scope.listarLivros(new Number($scope.numeroPagina - 1));
		}
	}
	
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
	}

});

// mensagem de sucesso
function sucesso(msg) {
	$.notify({
		message: msg

	}, {
		type: 'success',
		timer: 1000
	});
};

// mensagem de erro
function erro(msg) {
	$.notify({
		message: msg

	}, {
		type: 'danger',
		timer: 1000
	});
};

// faz a identificação da posição correta da cidade do registro para mostrar em edição
function buscarKeyJson(obj, key, value) {
	for (var i = 0; i < obj.length; i++) {
		if (obj[i][key] == value) {
			return i + 2;
		}
	}
	return null;
}
//identifica o navegador que está sendo usado o programa
function identific_nav() {
	var nav = navigator.userAgent.toLowerCase();
	if (nav.indexOf("msie") != -1) {
		return browser = "msie";
	} else if (nav.indexOf("opera") != -1) {
		return browser = "opera";
	} else if (nav.indexOf("mozilla") != -1) {
		if (nav.indexOf("firefox") != -1) {
			return browser = "firefox";
		} else if (nav.indexOf("firefox") != -1) {
			return browser = "mozilla";
		} else if (nav.indexOf("chrome") != -1) {
			return browser = "chrome";
		}
	} else {
		alert("Navegador desconhecido!");
	}
}

// carregar cidades quando usando o navegador chrome usando jQuery
function carregarCidadesChrome(estado) {
	if (identific_nav() === 'chrome') {// executa se for chrome
		$.get("cidades/listarchrome", { idEstado: estado.value }, function(data) {
			var json = JSON.parse(data);
			html = '<option value="">--Selecione--</option>';
			for (var i = 0; i < json.length; i++) {
				html += '<option value=' + json[i].id + '>' + json[i].nome + '</option>';
			}
			$('#selectCidades').html(html);
		});
	}
}

// adiciona a imagem ao campo html img
function visualizarImg() {
	var preview = document.querySelectorAll('img').item(1);
	var file = document.querySelector('input[type=file]').files[0];
	var reader = new FileReader();

	reader.onloadend = function() {
		preview.src = reader.result;// carrega em base64 a img
	};

	if (file) {
		reader.readAsDataURL(file);
	} else {
		preview.src = "";
	}

}