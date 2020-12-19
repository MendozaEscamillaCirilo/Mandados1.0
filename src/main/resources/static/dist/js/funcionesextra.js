function agregarproducto(id){
	Swal.fire({
        title: 'Ingrese la cantidad',
        input: 'number',
        inputAttributes: {
          autocapitalize: 'off'
        },
        showCancelButton: true,
        confirmButtonText: 'Look up',
        showLoaderOnConfirm: true,
        preConfirm: (login) => {

        },
        allowOutsideClick: () => !Swal.isLoading()
      }).then((result) => {
        if (result.isConfirmed) {
          Swal.fire({
            title: `${result.value.login}'s avatar`,
            imageUrl: result.value.avatar_url
          })
        }
      })
}
function eliminarcomercio(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarcomercio/"+id,
				success: function(res){
					Swal.fire('Inhabilitado correctamente!', '', 'success');
					// location.reload();
					window.location.href = "/listacomercio";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function eliminarRepartidor(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarrepartidor/"+id,
				success: function(res){
					Swal.fire('Inhabilitado correctamente!', '', 'success');
					// location.reload();
					window.location.href = "/listarepartidor";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function eliminarusuario(id){
	Swal.fire({
		title: 'Solo se va a inabilitar',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarusuario/"+id,
				success: function(res){
					Swal.fire('Inhabilitado correctamente!', '', 'success');
					window.location.href = "/usuarios";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function eliminarProducto(id){
	Swal.fire({
		title: '¿Está seguro?',
		text: 'No se va a borrar',
		showDenyButton: true,
		confirmButtonText: `Aceptar`,
		denyButtonText: `Cancelar`,
		allowOutsideClick: false
	  }).then((result) => {
		if (result.isConfirmed) {
			$.ajax({
				url:"/eliminarproducto/"+id,
				success: function(res){
					Swal.fire('eliminado correctamente!', '', 'success');
					window.location.href = "/listaproducto";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
		} else if (result.isDenied) {
			console.log("inside else");
		  	Swal.fire('No se eliminará el comercio', '', 'info')
		}
	  })
}
function addacarrito(id, nombre,comercio,precio){
	let cantidad = 0;
	Swal.fire({
		title: 'Ingresa la cantidad',
		input: 'number',
		inputAttributes: { min: 1, max: 20 },
		showCancelButton: true,
		confirmButtonText: 'Aceptar',
		showLoaderOnConfirm: true,
		preConfirm: (ncantidad) => {
			var cuerpo = document.getElementById("cuerpo");
			var hilera = document.createElement("tr");
		
			var celda = document.createElement("td");
			var txtnombre = document.createTextNode(nombre);
			celda.appendChild(txtnombre);
			hilera.appendChild(celda);
		
			var celda1 = document.createElement("td");
			var txtcomercio = document.createTextNode(comercio);
			celda1.appendChild(txtcomercio);
			hilera.appendChild(celda1);
		
			var celda2 = document.createElement("td");
			var txtcantidad = document.createTextNode(`${ncantidad}`);
			celda2.appendChild(txtcantidad);
			hilera.appendChild(celda2);
		
			var celda3 = document.createElement("td");
			var txttotal = document.createTextNode(precio * `${ncantidad}`);
			celda3.appendChild(txttotal);
			hilera.appendChild(celda3);
		
			var celda3 = document.createElement("td"); celda3.className = "text-center";
			var boton = document.createElement("button"); 
			boton.className = "btn btn-outline-danger";
			var ii = document.createElement("i"); ii.className = "fas fa-trash";
			boton.appendChild(ii);
			celda3.appendChild(boton);
			hilera.appendChild(celda3);
		
			cuerpo.appendChild(hilera);
		}
	  })
	

	// let datos = '';
	// console.log('id =>' + id);
	// console.log('nombre => ' + nombre);
	// let paratoast = (datos===undefined) ? true : false;
	// console.log(this.paratoast);
	// console.log(this.datos);
	// if (this.datos===undefined) { this.datos = nombre; }else{this.datos += ', ' + nombre;}
	
	// console.log(this.datos);
	// if(paratoast){
	// 	$(document).Toasts('create', {
	// 	body: this.datos,
	// 	title: 'Pedido',
	// 	subtitle: 'Subtitle',
	// 	icon: 'fas fa-cart-plus fa-lg',
	// 	});
	// }
	// Swal.fire({
	// 	title: '¿Está seguro?',
	// 	text: 'No se va a borrar',
	// 	showDenyButton: true,
	// 	confirmButtonText: `Aceptar`,
	// 	denyButtonText: `Cancelar`,
	// 	allowOutsideClick: false
	//   }).then((result) => {
	// 	if (result.isConfirmed) {
	// 		$.ajax({
	// 			url:"/obtcomercios/",
	// 			success: function(res){
	// 				console.log(res);
	// 				Swal.fire('eliminado correctamente!', '', 'success');
	// 				// window.location.href = "/listaproducto";
	// 			},
	// 			error: function(res){
	// 				console.log(res);
	// 				console.log(res.responseText);
	// 				Swal.fire('Sucedió un error!', '', 'error')
	// 			}
	// 		});
	// 	} else if (result.isDenied) {
	// 		console.log("inside else");
	// 	  	Swal.fire('No se eliminará el comercio', '', 'info')
	// 	}
	//   })
}
function confirmarPedido(){
	Swal.fire('Aún esto no funciona', '', 'info')
	// console.log(document.getElementById("tablapedido"));
}