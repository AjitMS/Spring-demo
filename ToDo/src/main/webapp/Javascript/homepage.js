var updatebookretrieval;
var notify;
var updatebook;
var deletebook;
var showbook;
var confirm;
var showlist;
var category;
$(document).ready(function(){

	notify = function(){
		alert('Alert');
	}
	
	showbook = function(bookName){
		
		console.log('Testing ShowBookModal');
		
		 
		  // bookName = button.data('whatever')
		  console.log('Bookname from showbook button is:' +bookName)
		           
			$.ajax({

				url : "HomepageController",
				
				
				data: {
					command: "showBook",
					bookname : bookName
				},		
				
				
				type : "POST",

				dataType : "json",
			})
			.done(function(json) {
				
				$('#showBookModal').modal('show');
				// $("#showbookform").reset();
				var html = " <thead> <tr> <th> Book Name </th> <th> Book Author </th> <th> Book Category </th> <th> Book Description </th> </tr> </thead> ";
				
				$.each(json, function(key, value){
					$('#showbooktable').html("");
					console.log(json);
					html+="<tbody><tr><td>"+value['bookName']+"</td> <td>"+value['bookAuthor']+"</td> <td>"+value['bookCategory']+"</td> <td>"+value['bookDescription']+"</td> </tr></tbody>";
					console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
				})
				
				$('#showbooktable').html(html);
				
			
			})
			.fail(function(xhr, status, errorThrown) {
				alert("Problem Retrieving Book !");
				console.log("Error: " + errorThrown);
				console.log("Status: " + status);
				console.dir(xhr);
			})
					
	}
	
	updatebook = function(bookName){
		var bookname = bookName;
		console.log('testing UpdateBook bookName: '+bookName);
		$.ajax({

			url : "HomepageController",
			
			
			data: {
				command: "updateBook",
				oldbookname : bookName
			},		
			
			type : "POST",

			dataType : "json",
		})
		.done(function(json) {
			window.alert('Update working ');
			console.log('json object is '+json);
			
			// funciton show update modal
			$('#updateBookModal').on('show', function(){
				var modal = $(this);
				  modal.find('.modal-title').text('Update Modal');  

			})
			console.log('Book details from json is');
			console.log(json['bookName']);
			console.log(json['bookAuthor']);
			console.log(json['bookCategory']);
			console.log(json['bookDescription']);
			
				$('#oldbookname').val(bookName);
				$('#ubookname').val(json['bookName']);
				$('#ubookauthor').val(json['bookAuthor']);
				$('#ubookcategory').val(json['bookCategory']);
				$('#ubookdescription').val(json['bookDescription']);
			$('#updateBookModal').modal('show');
			
			
			$('#updatebooksubmit').on("click", function(){

				
			var dataString = $('#updatebookform').serialize();

				
				$.ajax({

					url : "HomepageController",
					
					
					data: {
						command: "updateBook1",
						dataString : dataString
					},		
					
					type : "POST",

					dataType : "text",
				})	
				.done(function(text) {
					if(text === 'success'){
					window.alert('Updated !');
					console.log('inside done');
					$('#listBookModal').modal('hide');
					$('#updateBookModal').modal('hide');
					}
					else{
						alert("Book Name Exists.")
					}
				})
				.fail(function(xhr, status, errorThrown) {
					alert("Book does not Exist !");
					console.log("Error: " + errorThrown);
					console.log("Stastus: " + status);
					console.dir(xhr);
				})
			})
			
			
			// ("#updateBookModal").modal('show');
		})
		.fail(function(xhr, status, errorThrown) {
			alert("Problem Loading Modal !");
			console.log("Error: " + errorThrown);
			console.log("Status: " + status);
			console.dir(xhr);
		})
		
		
	}	  
	
	
	deletebook = function(bookName){
		
		console.log('Testing DeleteBookModal');
		  
		var test =  confirm('Are you sure you want to delete  ?');	
		
		if(test == true){
			$('#listBookModal').modal('hide');
			$.ajax({

				// The URL for the request
				url : "HomepageController",
				
				
				
				// The data to send (will be converted to a query string)
				/*
				 * data : { id : 123, dataString : dataString, command :
				 * "addBook", },
				 */

				data: {
					command: "deleteBook",
					confirm: confirm,
					bookname : bookName
				},
				
				
				// Whether this is a POST or GET request
				type : "POST",

				// The type of data we expect back
				dataType : "text",
			})
			// Code to run if the request succeeds (is done);
			// The response is passed to the functions
			.done(function(text) {
				window.alert('Book Deleted Successfully');
				
				
			})
			// Code to run if the request fails; the raw request and
			// status codes are passed to the function
			.fail(function(xhr, status, errorThrown) {
				alert("Book cannot be Deleted!");
				console.log("Error: " + errorThrown);
				console.log("Status: " + status);
				console.dir(xhr);
			})
			
			// Code to run regardless of success or failure;
			
			  .always(function(xhr, status) { 
				  $('#listBookModal').modal('show');   
				  });
			 			  
		}
	
	else{
		alert('Action Cancelled');
	}
	}	
	
	
	
	
	
	
	// CONFIG LIST_BOOK_MODAL
		$('#listBookModal').on('show.bs.modal', function (event) {
			
			  var button = $(event.relatedTarget) 
			  bookCategory = button.data('whatever')
			 var modal = $(this);
			  
			  if(bookCategory!= null) // category found in button
			  modal.find('.modal-title').text(bookCategory+'.');  
			
			console.log('Testing listBookModal');	
	    

			$.ajax({

				url : "HomepageController",

				data : {
					id : 123,
					command : "showList",
					category : bookCategory
				},

				type : "POST",

				dataType : "json",
			})
			.done(function(json) {
				var html = " <thead> <tr> <th>Book Name </th> <th> Action </th> </tr> </thead> "
				$.each(json, function(key, value){
					$('#booktable').html("");
					html+="<tbody><tr><td> <button class='btn btn-light' data-whatever='"+value['bookName']+"' onclick='showbook(\""+value['bookName']+"\");'> "+value['bookName']+" </button> </td>" +
							"<td> <button class= 'btn btn-primary btn-sm' onclick='updatebook(\""+value['bookName']+"\");' >Update</button> |" +
									" <button type='button' class= 'btn btn-danger btn-sm' onclick='deletebook(\""+value['bookName']+"\");');' >Delete</button>  </td> </tr></tbody>";
					console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					category = value['bookCategory'];
					console.log("Category is: "+category);
					modal.find('.modal-title').text(category+'.');
				})
				
				$('#booktable').html(html);
				
			
			})
			.fail(function(xhr, status, errorThrown) {
				console.log("Error: " + errorThrown);
				console.log("Status: " + status);
				console.dir(xhr);
			})

	});
		
		
	// CONFIG ADD_BOOK_MODAL
		 $('#addbooksubmit').on('click', function (event) {
			  // var button = $(event.relatedTarget) // Button that triggered
				// the modal
			  // var bookCategory = button.data('whatever') // Extract info
				// from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and
				// then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you
				// could use a data binding library or other methods instead.
			  // var modal = $(this)
			  // modal.find('.modal-title').text('Add new book')
			
			console.log('Testing AddBookModal');
			  
			  $("#addbookform").submit(function(e){
		           e.preventDefault();
		    });
			  
			dataString =  $("#addbookform").serialize();
			$('#addbookform')[0].reset();
				$.ajax({

					// The URL for the request
					url : "HomepageController",
					
					
					// The data to send (will be converted to a query string)
					/*
					 * data : { id : 123, dataString : dataString, command :
					 * "addBook", },
					 */

					data: {
						dataString,
						command: "addBook"
					},
					
					
					// Whether this is a POST or GET request
					type : "POST",

					// The type of data we expect back
					dataType : "text",
				})
				// Code to run if the request succeeds (is done);
				// The response is passed to the function
				.done(function(text) {
					
					
					if(text === 'success'){
					$('#addBookModal').modal('hide');
					$('#listBookModal').modal('show');
					window.alert('Book Added Successfully');
					}
					
					if(text === 'fail'){
						alert('Book Exists');
					}
					
					if(text === 'empty'){
						alert('Invalid Credentials');
					}
					/*
					 * var html = " <thead> <tr> <th> Book Name </th> <th>
					 * Action </th> </tr> </thead> " $.each(json, function(key,
					 * value){ $('#booktable').html(""); html+="<tbody><tr><td>"+value['bookName']+"</td>
					 * <td>"+value['bookName']+"</td> </tr></tbody>";
					 * console.log(' array content is '+value['bookName']+" "
					 * +value['bookCategory']); })
					 * 
					 * $('#booktable').html(html);
					 */
					
					// $('.content').html(value[bookName]+"
					// "+value[bookAuthor]+" "+value[bookCategory]);
				
				})
				// Code to run if the request fails; the raw request and
				// status codes are passed to the function
				.fail(function(xhr, status, errorThrown) {
					
					$("#addbookform")[0].reset();
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				
				// Code to run regardless of success or failure;
				/*
				 * .always(function(xhr, status) { alert("The request is
				 * complete!"); });
				 */			  
			  
		 });
		 


		// CONFIG SHOW_BOOK_MODAL
		// $(button).live('click', function (event) {
		// alert('Entered by id');
		// })


		 $('.showbooksubmit').on("click",  function (event) {
			 
			alert('Entered into show book');	
			console.log('Testing ShowBookModal');
			  
		    
			
			  var bookName = $(event.target).text;
			  console.log('Bookname from showbook link is: "+bookName')
			  bookName = button.data('whatever')
			  console.log("Bookname from showbook button is: "+bookName);
	          
				$.ajax({

					url : "HomepageController",
					
					
					data: {
						command: "showBook",
						bookname : bookName
					},		
					
					
					type : "POST",

					dataType : "json",
				})
				.done(function(json) {
					
					$('#listBookModal').modal('hide');
					$('#showBookModal').modal('show');
					// $("#showbookform").reset();
						
					window.alert('Book Information is retrieved successfully');
					$.each(json, function(key, value){
						$('#showbooktable').html("");
						html+="<tbody><tr><td>"+value['bookName']+"</td> <td>"+value['bookName']+"</td> </tr></tbody>";
						console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					})
					
					$('#showbooktable').html(html);
					
				
				})
				.fail(function(xhr, status, errorThrown) {
					alert("Problem Retrieving Book !");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				
						  
			  
		 });
		 
		// CONFIG UPDATE_BOOK_MODAL
		
	$('#updatebook').on('click', function (event) {
		// var button = $(event.relatedTarget) // Button that triggered the
		// modal
		// var bookCategory = button.data('whatever') // Extract info from
		// data-* attributes
		// If necessary, you could initiate an AJAX request here (and then do
		// the updating in a callback).
		// Update the modal's content. We'll use jQuery here, but you could use
		// a data binding library or other methods instead.
		// var modal = $(this)
		// modal.find('.modal-title').text('Add new book')
	  
	  console.log('Testing ShowBookModal');
		
		$("#showbookform").submit(function(e){
			 e.preventDefault();
	  });
	  
		var bookName = $(event.target).text;
		console.log("Bookname from showbook modal is: "+bookName);
		
		  $.ajax({

			  // The URL for the request
			  url : "HomepageController",
			  
			  
			  // The data to send (will be converted to a query string)
			  /*
				 * data : { id : 123, dataString : dataString, command :
				 * "addBook", },
				 */

			  data: {
				  command: "showBook",
				  bookname : bookName
			  },
			  
			  
			  // Whether this is a POST or GET request
			  type : "POST",

			  // The type of data we expect back
			  dataType : "text",
		  })
		  // Code to run if the request succeeds (is done);
		  // The response is passed to the function
		  .done(function(text) {
			  
			  $('#addBookModal').modal('hide');
			  $('#listBookModal').modal('show');
				  
			  window.alert('Book Added Successfully');
			  /*
				 * var html = " <thead> <tr> <th> Book Name </th> <th> Action
				 * </th> </tr> </thead> " $.each(json, function(key, value){
				 * $('#booktable').html(""); html+="<tbody><tr><td>"+value['bookName']+"</td>
				 * <td>"+value['bookName']+"</td> </tr></tbody>";
				 * console.log(' array content is '+value['bookName']+" "
				 * +value['bookCategory']); })
				 * 
				 * $('#booktable').html(html);
				 */
			  
			  // $('.content').html(value[bookName]+" "+value[bookAuthor]+"
				// "+value[bookCategory]);
		  
		  })
		  // Code to run if the request fails; the raw request and
		  // status codes are passed to the function
		  .fail(function(xhr, status, errorThrown) {
			  alert("Book already exists!");
			  console.log("Error: " + errorThrown);
			  console.log("Status: " + status);
			  console.dir(xhr);
		  })
		  
		  // Code to run regardless of success or failure;
		  /*
			 * .always(function(xhr, status) { alert("The request is
			 * complete!"); });
			 */			  
		
   }); 
		 
		// CONFIG DELETE_BOOK_MODAL
		 $('#deletebook').on('click', function (event) {
			  // var button = $(event.relatedTarget) // Button that triggered
				// the modal
			  // var bookCategory = button.data('whatever') // Extract info
				// from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and
				// then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you
				// could use a data binding library or other methods instead.
			  // var modal = $(this)
			  // modal.find('.modal-title').text('Add new book')
			
			console.log('Testing ShowBookModal');
			  
			  $("#showbookform").submit(function(e){
		           e.preventDefault();
		    });
			
			  var bookName = $(event.target).text;
			  console.log("Bookname from showbook modal is: "+bookName);
	          
				$.ajax({

					// The URL for the request
					url : "HomepageController",
					
					
					// The data to send (will be converted to a query string)
					/*
					 * data : { id : 123, dataString : dataString, command :
					 * "addBook", },
					 */

					data: {
						command: "showBook",
						bookname : bookName
					},
					
					
					// Whether this is a POST or GET request
					type : "POST",

					// The type of data we expect back
					dataType : "text",
				})
				// Code to run if the request succeeds (is done);
				// The response is passed to the function
				.done(function(text) {
					
					$('#addBookModal').modal('hide');
					$('#listBookModal').modal('show');
						
					window.alert('Book Added Successfully');
					/*
					 * var html = " <thead> <tr> <th> Book Name </th> <th>
					 * Action </th> </tr> </thead> " $.each(json, function(key,
					 * value){ $('#booktable').html(""); html+="<tbody><tr><td>"+value['bookName']+"</td>
					 * <td>"+value['bookName']+"</td> </tr></tbody>";
					 * console.log(' array content is '+value['bookName']+" "
					 * +value['bookCategory']); })
					 * 
					 * $('#booktable').html(html);
					 */
					
					// $('.content').html(value[bookName]+"
					// "+value[bookAuthor]+" "+value[bookCategory]);
				
				})
				// Code to run if the request fails; the raw request and
				// status codes are passed to the function
				.fail(function(xhr, status, errorThrown) {
					alert("Book already exists!");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				
				// Code to run regardless of success or failure;
				/*
				 * .always(function(xhr, status) { alert("The request is
				 * complete!"); });
				 */			  
			  
		 });

	});
			
