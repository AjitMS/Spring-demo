/**
 * 
 */$(document).ready(function(){
	var bookCategory
	
	//CONFIG LIST_BOOK_MODAL
		$('#listBookModal').on('show.bs.modal', function (event) {
			  var button = $(event.relatedTarget) // Button that triggered the modal
			  bookCategory = button.data('whatever') // Extract info from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			  var modal = $(this);
			  
			  if(bookCategory!= null) //category found in button
			  modal.find('.modal-title').text(bookCategory+'.');  
			
			console.log('Testing listBookModal');	
	    

			$.ajax({

				// The URL for the request
				url : "HomepageController",

				// The data to send (will be converted to a query string)
				data : {
					id : 123,
					command : "showList",
					category : bookCategory
				},

				// Whether this is a POST or GET request
				type : "POST",

				// The type of data we expect back
				dataType : "json",
			})
			// Code to run if the request succeeds (is done);
			// The response is passed to the function
			.done(function(json) {
					
				var html = " <thead> <tr> <th> Book Name </th> <th> Action </th> </tr> </thead> "
				$.each(json, function(key, value){
					$('#booktable').html("");
					html+="<tbody><tr><td> <button class=\"  btn btn-light showbooksubmit \" >"+value['bookName']+"</button> </td>  <td> <button class= \" btn btn-primary btn-sm \" >Update</button> | <button class= \" btn btn-danger btn-sm \" value= \" delete \" >Delete</button>  </td> </tr></tbody>";
					console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					category = value['bookCategory'];
					console.log("Category is: "+category);
					modal.find('.modal-title').text(category+'.');
				})
				//console.log("Category is: "+category);
				//category found in from shown books
				
				$('#booktable').html(html);
				
				//$('.content').html(value[bookName]+" "+value[bookAuthor]+" "+value[bookCategory]);
			
			})
			// Code to run if the request fails; the raw request and
			// status codes are passed to the function
			.fail(function(xhr, status, errorThrown) {
				console.log("Error: " + errorThrown);
				console.log("Status: " + status);
				console.dir(xhr);
			})
			
			
			
			// Code to run regardless of success or failure;
			/* .always(function(xhr, status) {
				alert("The request is complete!");
			}); */
			
	});
		
		
	//CONFIG ADD_BOOK_MODAL
		 $('#addbooksubmit').on('click', function (event) {
			  //var button = $(event.relatedTarget) // Button that triggered the modal
			  //var bookCategory = button.data('whatever') // Extract info from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			  //var modal = $(this)
			  //modal.find('.modal-title').text('Add new book')
			
			console.log('Testing AddBookModal');
			  
			  $("#addbookform").submit(function(e){
		           e.preventDefault();
		    });
			  
			dataString =  $("#addbookform").serialize();  
				$.ajax({

					// The URL for the request
					url : "HomepageController",
					
					
					// The data to send (will be converted to a query string)
					/*data : {
						id : 123,
						dataString : dataString,
						command : "addBook",
					},*/

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
					
					$('#addBookModal').modal('hide');
					$('#listBookModal').modal('show');
						
					window.alert('Book Added Successfully');
					/*var html = " <thead> <tr> <th> Book Name </th> <th> Action </th> </tr> </thead> "
					$.each(json, function(key, value){
						$('#booktable').html("");
						html+="<tbody><tr><td>"+value['bookName']+"</td> <td>"+value['bookName']+"</td> </tr></tbody>";
						console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					})
					
					$('#booktable').html(html);*/
					
					//$('.content').html(value[bookName]+" "+value[bookAuthor]+" "+value[bookCategory]);
				
				})
				// Code to run if the request fails; the raw request and
				// status codes are passed to the function
				.fail(function(xhr, status, errorThrown) {
					
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				
				// Code to run regardless of success or failure;
				/* .always(function(xhr, status) {
					alert("The request is complete!");
				}); */			  
			  
		 });
		 
		//CONFIG SHOW_BOOK_MODAL
		$('#showbooksubmit').on('click', function (event) {
			alert('Enter');
		}) 


		 $('.showbooksubmit').on('click', function (event) {
			  //var button = $(event.relatedTarget) // Button that triggered the modal
			  //var bookCategory = button.data('whatever') // Extract info from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			  //var modal = $(this)
			  //modal.find('.modal-title').text('Add new book')
			
			console.log('Testing ShowBookModal');
			  
			  $("showbooksubmit").submit(function(e){
		           e.preventDefault();
		    });
			
			  var bookName = $(event.target).text;
			  console.log('Bookname from showbook link is: "+bookName')
			  bookName = button.data('whatever')
			  console.log("Bookname from showbook button is: "+bookName);
	          
				$.ajax({

					// The URL for the request
					url : "HomepageController",
					
					
					// The data to send (will be converted to a query string)
					/*data : {
						id : 123,
						dataString : dataString,
						command : "addBook",
					},*/

					data: {
						command: "showBook",
						bookname : bookName
					},		
					
					
					// Whether this is a POST or GET request
					type : "POST",

					// The type of data we expect back
					dataType : "json",
				})
				// Code to run if the request succeeds (is done);
				// The response is passed to the function
				.done(function(json) {
					
					$('#listBookModal').modal('hide');
					$('#showBookModal').modal('show');
					//$("#showbookform").reset();
						
					window.alert('Book Information is retrieved successfully');
					//var html = " <thead> <tr> <th> Book Name </th> <th> Action </th> </tr> </thead> "
					$.each(json, function(key, value){
						$('#showbooktable').html("");
						html+="<tbody><tr><td>"+value['bookName']+"</td> <td>"+value['bookName']+"</td> </tr></tbody>";
						console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					})
					
					$('#showbooktable').html(html);
					
					//$('.content').html(value[bookName]+" "+value[bookAuthor]+" "+value[bookCategory]);
				
				})
				// Code to run if the request fails; the raw request and
				// status codes are passed to the function
				.fail(function(xhr, status, errorThrown) {
					alert("Problem Retrieving Book !");
					console.log("Error: " + errorThrown);
					console.log("Status: " + status);
					console.dir(xhr);
				})
				
				// Code to run regardless of success or failure;
				/* .always(function(xhr, status) {
					alert("The request is complete!");
				}); */			  
			  
		 });
		 
		//CONFIG UPDATE_BOOK_MODAL
		 $('#showbooksubmit').on('click', function (event) {
			  //var button = $(event.relatedTarget) // Button that triggered the modal
			  //var bookCategory = button.data('whatever') // Extract info from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			  //var modal = $(this)
			  //modal.find('.modal-title').text('Add new book')
			
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
					/*data : {
						id : 123,
						dataString : dataString,
						command : "addBook",
					},*/

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
					/*var html = " <thead> <tr> <th> Book Name </th> <th> Action </th> </tr> </thead> "
					$.each(json, function(key, value){
						$('#booktable').html("");
						html+="<tbody><tr><td>"+value['bookName']+"</td> <td>"+value['bookName']+"</td> </tr></tbody>";
						console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					})
					
					$('#booktable').html(html);*/
					
					//$('.content').html(value[bookName]+" "+value[bookAuthor]+" "+value[bookCategory]);
				
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
				/* .always(function(xhr, status) {
					alert("The request is complete!");
				}); */			  
			  
		 });
		 
		//CONFIG DELETE_BOOK_MODAL
		 $('#showbooksubmit').on('click', function (event) {
			  //var button = $(event.relatedTarget) // Button that triggered the modal
			  //var bookCategory = button.data('whatever') // Extract info from data-* attributes
			  // If necessary, you could initiate an AJAX request here (and then do the updating in a callback).
			  // Update the modal's content. We'll use jQuery here, but you could use a data binding library or other methods instead.
			  //var modal = $(this)
			  //modal.find('.modal-title').text('Add new book')
			
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
					/*data : {
						id : 123,
						dataString : dataString,
						command : "addBook",
					},*/

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
					/*var html = " <thead> <tr> <th> Book Name </th> <th> Action </th> </tr> </thead> "
					$.each(json, function(key, value){
						$('#booktable').html("");
						html+="<tbody><tr><td>"+value['bookName']+"</td> <td>"+value['bookName']+"</td> </tr></tbody>";
						console.log(' array content is '+value['bookName']+" " +value['bookCategory']);
					})
					
					$('#booktable').html(html);*/
					
					//$('.content').html(value[bookName]+" "+value[bookAuthor]+" "+value[bookCategory]);
				
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
				/* .always(function(xhr, status) {
					alert("The request is complete!");
				}); */			  
			  
		 });

	});
			
