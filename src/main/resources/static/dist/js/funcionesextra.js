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
function addacarrito(id){
	Swal.fire({
		title: 'Ingresa la cantidad',
		input: 'number',
		inputAttributes: { min: 1, max: 20, required: true },
		showCancelButton: true,
		confirmButtonText: 'Aceptar',
		showLoaderOnConfirm: true,
		preConfirm: (ncantidad) => {
			var ids = "";
			var valores = "";
			if($('#tablapedido').length){
				for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
					ids += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText;
					valores += "," + $('#tablapedido')[0].children[1].children[i].cells[3].innerText;
				}
			}else{
				ids = "1000";
			}
			$('#divconfirmarpedido').load("/cargartablaproductosdepedido/"+id 
									+ "?cantidad="+ncantidad
									+ "&lista="+ids
									+ "&cantidades="+valores
									);
		}
	  })
}
function eliminardecarrito(){
	console.log('en eliminarr');
}
function confirmarPedido(){
	var nombre = document.getElementById('cliente-nombres');
	var primerapellido = document.getElementById('cliente-primerapellido');
	var segundoapellido = document.getElementById('cliente-segundapellido');
	var calle = document.getElementById('cliente-calle');
	var numero = document.getElementById('cliente-numero');
	var colonia = document.getElementById('cliente-colonia');
	var municipio = document.getElementById('cliente-municipio');
	var telefono = document.getElementById('cliente-telefono');
	if (nombre.value===""||primerapellido.value===""||calle.value===""||numero.value===""||colonia.value===""||municipio.value===""||telefono.value==="") {
		Swal.fire('Revisa que todos los campos estén llenos!', '', 'error');
	}else{
		segundoapellido=(segundoapellido===null)?" ": segundoapellido.value;
		console.log(nombre.value);
		console.log(primerapellido.value);
		console.log(segundoapellido);
		console.log(calle);
		console.log(numero);
		console.log(colonia);
		console.log(municipio);
		console.log(telefono);
		var ids = "";
		var valores = "";
		if($('#tablapedido').length){
			for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
				ids += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText;
				valores += "," + $('#tablapedido')[0].children[1].children[i].cells[3].innerText;
			}
		}
		
		$.ajax({
			url:"/registrarpedido"
								+"?nombre="+nombre.value
								+"&primerapellido="+primerapellido.value
								+"&segundoapellido="+segundoapellido
								+"&calle="+calle.value
								+"&numero="+numero.value
								+"&colonia="+colonia.value
								+"&municipio="+municipio.value
								+"&telefono="+telefono.value
								+"&ids="+ids
								+"&valores="+valores
								,
			success: function(res){
				Swal.fire('insertado correctamente!', '', 'success');
				window.location.href = "/listapedido";
			},
			error: function(res){
				Swal.fire('Sucedió un error!', '', 'error')
			}
		});
	}
	// Swal.fire({
	// 	title: 'Nombre del cliente(incluyendo apellidos)',
	// 	input: 'text',
	// 	inputAttributes: { required: true },
	// 	showCancelButton: true,
	// 	confirmButtonText: 'Aceptar',
	// 	showLoaderOnConfirm: true,
	// 	preConfirm: (nombre) => {
	// 		Swal.fire({
	// 			title: 'Telefono',
	// 			input: 'number',
	// 			inputAttributes: { required: true },
	// 			showCancelButton: true,
	// 			confirmButtonText: 'Aceptar',
	// 			showLoaderOnConfirm: true,
	// 			preConfirm: (telefono) => {
	// 				Swal.fire({
	// 					title: 'Dirección(calle,numero,colonia)',
	// 					input: 'text',
	// 					inputAttributes: { required: true },
	// 					showCancelButton: true,
	// 					confirmButtonText: 'Aceptar',
	// 					showLoaderOnConfirm: true,
	// 					preConfirm: (direccion) => {
	// 						console.log(`${nombre}` + ' ' + `${telefono}` + ' ' + `${direccion}`);
	// 					}
	// 				});
	// 			}
	// 		});
	// 	}
	// });
	// var tabla = document.getElementById("tablapedido");
	// var array = tabla.children.cuerpo.innerText;
	// const regex = /	/gi;
	// const regex2 = /\n/gi;
	// var final = array.replace(regex,'-');
	// var fin2 = final.replace(regex2,'&')
	// // console.log(fin2);
	// $.ajax({
	// 	url:"/registrarpedido/"+fin2,
	// 	success: function(res){
	// 		Swal.fire('insertado correctamente!', '', 'success');
	// 		window.location.href = "/listapedido";
	// 	},
	// 	error: function(res){
	// 		Swal.fire('Sucedió un error!', '', 'error')
	// 	}
	// });
}