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
			Swal.fire({
				title: 'Comentario',
				text: 'Si no hay comentarios ingrese NO',
				input: 'text',
				inputAttributes: {required: true},
				showCancelButton: true,
				confirmButtonText:'Aceptar',
				showLoaderOnConfirm:true,
				preConfirm: (comentario) => {
					const regex = / /gi;
					var ids = "";
					var valores = "";
					var comentarios = "";
					if($('#tablapedido').length){
						for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
							ids += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText;
							valores += "," + $('#tablapedido')[0].children[1].children[i].cells[3].innerText;
							comentarios += ("," + $('#tablapedido')[0].children[1].children[i].cells[5].innerText).replace(regex,'+');
						}
					}else{
						ids = "1000";
					}
					var comentario = comentario.replace(regex,'+');
					console.log(comentario);
					console.log(comentarios);
					$('#divconfirmarpedido').load("/cargartablaproductosdepedido/"+id 
											+ "?cantidad="+ncantidad
											+ "&lista="+ids
											+ "&cantidades="+valores
											+ "&comentario="+comentario
											+ "&comentarios="+comentarios
											);
				}
			});
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
		// console.log(nombre.value);
		// console.log(primerapellido.value);
		// console.log(segundoapellido);
		// console.log(calle);
		// console.log(numero);
		// console.log(colonia);
		// console.log(municipio);
		// console.log(telefono);
		var ids = "";
		var valores = "";
		var comentarios = "";
		if($('#tablapedido').length){
			for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
				ids += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText;
				valores += "," + $('#tablapedido')[0].children[1].children[i].cells[3].innerText;
				comentarios += "," + $('#tablapedido')[0].children[1].children[i].cells[5].innerText;
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
								+"&comentarios="+comentarios
								+"&sincomercio="+"no"
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
}
async function confirmarPedidoSincomercio(){
	var nombre = document.getElementById('cliente-nombres');
	var primerapellido = document.getElementById('cliente-primerapellido');
	var segundoapellido = document.getElementById('cliente-segundapellido');
	var calle = document.getElementById('cliente-calle');
	var numero = document.getElementById('cliente-numero');
	var colonia = document.getElementById('cliente-colonia');
	var municipio = document.getElementById('cliente-municipio');
	var telefono = document.getElementById('cliente-telefono');
	var mandado = document.getElementById('mandado').value;
	if (nombre.value===""||primerapellido.value===""||calle.value===""||numero.value===""||colonia.value===""||municipio.value===""||telefono.value===""||mandado.value==="") {
		Swal.fire('Revisa que todos los campos estén llenos!', '', 'error');
	}else{
		segundoapellido=(segundoapellido===null)?" ": segundoapellido.value;
		var productos = "";
		var presentaciones = "";
		var comentarios = "";
		const regex = / /gi;
		if($('#tablapedido').length){
			for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
				productos += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText.replace(regex,'+');
				presentaciones += "," + $('#tablapedido')[0].children[1].children[i].cells[1].innerText.replace(regex,'+');
				comentarios += "," + $('#tablapedido')[0].children[1].children[i].cells[2].innerText.replace(regex,'+');
			}
		}

		Swal.fire({
			title: 'REGISTRANDO PEDIDO...ESPERA UN MOMENTO',
			onOpen: function () {
			  Swal.showLoading()
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
									+"&ids="+productos
									+"&valores="+presentaciones
									+"&comentarios="+comentarios
									+"&sincomercio="+"si"
									+"&mandado="+mandado
									,
				success: function(res){
					Swal.fire('insertado correctamente!', '', 'success');
					window.location.href = "/listapedido";
				},
				error: function(res){
					Swal.fire('Sucedió un error!', '', 'error')
				}
			});
			  setTimeout(function () {
				Swal.close()
			  }, 10000)
			}
		  })
		
	}
}
function addacarritosincomercio(){
	Swal.fire({
		title: 'Ingresa el producto solicitado',
		text: 'El nombre del producto',
		input: 'text',
		inputAttributes: {required: true},
		showCancelButton: true,
		confirmButtonText:'Aceptar',
		showLoaderOnConfirm:true,
		preConfirm: (producto) => {
			Swal.fire({
				title: 'Ingresa presentación o cantidad',
				text: 'ejemplo: 1 kg, 1 litro, una bolsa',
				input: 'text',
				inputAttributes: {required: true},
				showCancelButton: true,
				confirmButtonText:'Aceptar',
				showLoaderOnConfirm:true,
				preConfirm: (presentacion) => {
					Swal.fire({
						title: 'Comentario',
						text: 'Si no hay comentarios ingrese NO',
						input: 'text',
						inputAttributes: {required: true},
						showCancelButton: true,
						confirmButtonText:'Aceptar',
						showLoaderOnConfirm:true,
						preConfirm: (comentario) => {

							var productos = "";
							var presentaciones = "";
							var comentarios = "";
							const regex = / /gi;
							if($('#tablapedido').length){
								for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
									productos += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText.replace(regex,'+');
									presentaciones += "," + $('#tablapedido')[0].children[1].children[i].cells[1].innerText.replace(regex,'+');
									comentarios += "," + $('#tablapedido')[0].children[1].children[i].cells[2].innerText.replace(regex,'+');
								}
							}
							$('#divconfirmarpedido').load("/agregaralcarritosincomercio"
											+"?producto="+producto.replace(regex,'+')
											+"&presentacion="+presentacion.replace(regex,'+')
											+"&comentario="+comentario.replace(regex,'+')
											+"&productos="+productos
											+"&presentaciones="+presentaciones
											+"&comentarios="+comentarios
											);
							// $.ajax({
							// 	url:"/agregaralcarritosincomercio"
							// 						+"?producto="+producto
							// 						+"&presentacion="+presentacion
							// 						+"&comentario="+comentario
							// 						,
							// 	success: function(res){
									
							// 		Swal.fire(res, '', 'success');
							// 		// window.location.href = "/listapedido";
							// 	},
							// 	error: function(res){
							// 		Swal.fire('Sucedió un error!', '', 'error')
							// 	}
							// });
						}
					});
				}
			});
		}
	});
}


async function addacarritosincomercio2(){
	
	const { value: formValues } = await Swal.fire({
	title: 'Ingresar un producto',
	html:
	  '<input id="swal-input1" class="swal2-input" placeholder="CANTIDAD(2, 1 kg, etc..)">' +
	  '<input id="swal-input2" class="swal2-input" placeholder="NOMBRE(hamburguesa, tomate,...)">'+
	  '<input id="swal-input3" class="swal2-input" placeholder="COMENTARIO(sin tomate, grande,...)">',
	focusConfirm: true,
	allowEnterKey: true, // default value
	preConfirm: () => {
	  return [
		document.getElementById('swal-input1').value,
		document.getElementById('swal-input2').value,
		document.getElementById('swal-input3').value
	  ]
	}
  })
  
  console.log(formValues[1]);

  var presentacion = formValues[0];
  var producto = formValues[1];
  var comentario = formValues[2];

  var presentaciones = "";
  var productos = "";
  var comentarios = "";
  const regex = / /gi;
  if($('#tablapedido').length){
	// if(document.getElementById("tablapedido").rows.length>1){
	  for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
		presentaciones += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText.replace(regex,'+');
		  productos += "," + $('#tablapedido')[0].children[1].children[i].cells[1].innerText.replace(regex,'+');
		  comentarios += "," + $('#tablapedido')[0].children[1].children[i].cells[2].innerText.replace(regex,'+');
	  }
//   }
}

  try{
  $('#divconfirmarpedido').load("/agregaralcarritosincomercio"
				  +"?producto="+producto.replace(regex,'+')
				  +"&presentacion="+presentacion.replace(regex,'+')
				  +"&comentario="+comentario.replace(regex,'+')
				  +"&productos="+productos
				  +"&presentaciones="+presentaciones
				  +"&comentarios="+comentarios
				  );
				  if (formValues) {
					Swal.fire(JSON.stringify(formValues[0]+" "+formValues[1]+ " AGREGADO"));
				  }
  }
  catch(x){
	Swal.fire({
		icon: 'error',
		title: 'Oops...',
		text: 'Ocurrio un error, FAVOR DE RECARGAR LA PAGINA',
		// footer: '<a href>Why do I have this issue?</a>'
	  })

  }

}

function addcomercio(){
	
	Swal.fire({
	title: 'Ingresar el comercio, punto de recoleccion',
	html:
	  '<input id="swal-input1" class="swal2-input" placeholder="NOMBRE(Taqueria Juanito)">' +
	  '<input id="swal-input2" class="swal2-input" placeholder="NOMBRE(Esc. Naval Militar #215)">'+
	  '<input id="swal-input3" class="swal2-input" placeholder="REFERENCIA(Cerca de ...)">',
	focusConfirm: false,
	preConfirm: () => {
	  return [
		document.getElementById('swal-input1').value,
		document.getElementById('swal-input2').value,
		document.getElementById('swal-input3').value
	  ]
	}
  })
  
  if (formValues) {
	Swal.fire(JSON.stringify(formValues))
  }
}

async function editarsincomercio(indice){

var presentacion = $('#presentacion'+indice).val();
var producto = $('#producto'+indice).val();
var comentario = $('#comentarios'+indice).val();

console.log(indice+' '+presentacion+' '+producto+' '+comentario);

var productos = "";
var presentaciones = "";
var comentarios = "";
const regex = / /gi;

for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
	if(i==indice){
		productos += "," +producto;
		presentaciones += "," + presentacion;
		comentarios += "," + comentario;
	}
	else{
	productos += "," + $('#tablapedido')[0].children[1].children[i].cells[1].innerText.replace(regex,'+');
	presentaciones += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText.replace(regex,'+');
	comentarios += "," + $('#tablapedido')[0].children[1].children[i].cells[2].innerText.replace(regex,'+');
	console.log(i);
	}
}
$('#divconfirmarpedido').load("/eliminardelcarritosincomercio"
+"?productos="+productos
+"&presentaciones="+presentaciones
+"&comentarios="+comentarios
);
$('body').removeClass('modal-open');
$('.modal-backdrop').remove();
}

async function eliminardelcarritosincomercio(indice, producto, presentacion, comentario){
	console.log(indice+" soy "+producto+" adios");
	var productos = "";
	var presentaciones = "";
	var comentarios = "";
	const regex = / /gi;
	console.log(document.getElementById("tablapedido").rows.length);
	if(document.getElementById("tablapedido").rows.length>1){
		for (let i = 0; i < $('#tablapedido')[0].children[1].children.length; i++) {
			if(i!=indice){
			productos += "," + $('#tablapedido')[0].children[1].children[i].cells[1].innerText.replace(regex,'+');
			presentaciones += "," + $('#tablapedido')[0].children[1].children[i].cells[0].innerText.replace(regex,'+');
			comentarios += "," + $('#tablapedido')[0].children[1].children[i].cells[2].innerText.replace(regex,'+');
			console.log(i);
			}
		}
		$('#divconfirmarpedido').load("/eliminardelcarritosincomercio"
		+"?productos="+productos
		+"&presentaciones="+presentaciones
		+"&comentarios="+comentarios
		);

	}else{
		$("#tablapedido > tbody").empty();

}
}