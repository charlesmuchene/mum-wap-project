$(function () {
	$("#priority").on('click', sortTable);
});

function sortTable() {
	var table, rows, switching, i, x, y, shouldSwitch;
	table = document.getElementById("tblTasks");
	switching = true;
	/*Make a loop that will continue until
	no switching has been done:*/
	while (switching) {
		//start by saying: no switching is done:
		switching = false;
		rows = table.rows;
		/*Loop through all table rows (except the
		first, which contains table headers):*/
		for (i = 1; i < (rows.length - 1); i++) {
			//start by saying there should be no switching:
			shouldSwitch = false;
			/*Get the two elements you want to compare,
			one from current row and one from the next:*/
			x = rows[i].getElementsByTagName("TD")[2];
			y = rows[i + 1].getElementsByTagName("TD")[2];
			//check if the two rows should switch place:
			if (x.innerHTML.toLowerCase() > y.innerHTML.toLowerCase()) {
				//if so, mark as a switch and break the loop:
				shouldSwitch = true;
				break;
			}
		}
		if (shouldSwitch) {
			/*If a switch has been marked, make the switch
			and mark that a switch has been done:*/
			rows[i].parentNode.insertBefore(rows[i + 1], rows[i]);
			switching = true;
		}
	}
}

tasksController = function () {

	function errorLogger(errorCode, errorMessage) {
		console.log(errorCode + ':' + errorMessage);
	}

	var taskPage;
	var initialised = false;

	/**
	 * makes json call to server to get task list.
	 * currently just testing this and writing return value out to console
	 * 111917kl
	 */
	function retrieveTasksServer() {
		$.get('TaskServlet')
			.done(displayTasksServer.bind());
	}

	/**
	 * 111917kl
	 * callback for retrieveTasksServer
	 * @param data
	 */
	function displayTasksServer(data) { //this needs to be bound to the tasksController -- used bind in retrieveTasksServer 111917kl
		console.log(data);
		tasksController.loadServerTasks(data);
	}

	function taskCountChanged() {
		var count = $(taskPage).find('#tblTasks tbody tr').length;
		$('footer').find('#taskCount').text(count);
	}

	function clearTask() {
		$(taskPage).find('form').fromObject({});
	}

	function renderTable() {
		$.each($(taskPage).find('#tblTasks tbody tr'), function (idx, row) {
			var due = Date.parse($(row).find('[datetime]').text());
			if (due.compareTo(Date.today()) < 0) {
				$(row).addClass("overdue");
			} else if (due.compareTo((2).days().fromNow()) <= 0) {
				$(row).addClass("warning");
			}
		});
	}

	return {
		init: function (page, callback) {
			if (initialised) {
				callback()
			} else {
				taskPage = page;
				storageEngine.init(function () {
					storageEngine.initObjectStore('task', function () {
						callback();
					}, errorLogger)
				}, errorLogger);
				$(taskPage).find('[required="required"]').prev('label').append('<span>*</span>').children('span').addClass('required');
				$(taskPage).find('tbody tr:even').addClass('even');

				$(taskPage).find('#btnAddTask').click(function (evt) {
					evt.preventDefault();
					$(taskPage).find('#taskCreation').removeClass('not');
				});
				$(taskPage).find('#addTeamButton').click(function (evt) {
					evt.preventDefault();
					$(taskPage).find('.teamWrapper').removeClass('not');
				});

				$(taskPage).find('#addUserButton').click(function (evt) {
					evt.preventDefault();
					$(taskPage).find('.userWrapper').removeClass('not');
				});

				$(taskPage).find('#addUserButton').click(function (evt) {
					evt.preventDefault();
					$(taskPage).find('#userCreation').removeClass('not');
				});

				/**     * 11/19/17kl        */
				$(taskPage).find('#btnRetrieveTasks').click(function (evt) {
					evt.preventDefault();
					console.log('making ajax call');
					retrieveTasksServer();
				});

				$(taskPage).find('#tblTasks tbody').on('click', 'tr', function (evt) {
					$(evt.target).closest('td').siblings().andSelf().toggleClass('rowHighlight');
				});

				$(taskPage).find('#tblTasks tbody').on('click', '.deleteRow',
					function (evt) {
						storageEngine.delete('task', $(evt.target).data().taskId,
							function () {
								$(evt.target).parents('tr').remove();
								taskCountChanged();
							}, errorLogger);

					}
				);

				$(taskPage).find('#tblTasks tbody').on('click', '.editRow',
					function (evt) {
						$(taskPage).find('#taskCreation').removeClass('not');
						storageEngine.findById('task', $(evt.target).data().taskId, function (task) {
							$(taskPage).find('form').fromObject(task);
						}, errorLogger);
					}
				);

				$(taskPage).find('#clearTask').click(function (evt) {
					evt.preventDefault();
					clearTask();
				});

				$(taskPage).find('#tblTasks tbody').on('click', '.completeRow', function (evt) {
					storageEngine.findById('task', $(evt.target).data().taskId, function (task) {
						task.complete = true;
						storageEngine.save('task', task, function () {
							tasksController.loadTasks();
						}, errorLogger);
					}, errorLogger);
				});

				$(taskPage).find('#saveTask').click(function (evt) {
					evt.preventDefault();
					if ($(taskPage).find('form').valid()) {
						var task = $(taskPage).find('form').toObject();
						storageEngine.save('task', task, function () {
							$(taskPage).find('#tblTasks tbody').empty();
							tasksController.loadTasks();
							clearTask();
							$(taskPage).find('#taskCreation').addClass('not');
						}, errorLogger);
					}
				});
				$(taskPage).find('#teamClose').click(function (evt) {
					evt.preventDefault();
					$(taskPage).find('.teamWrapper').addClass('not');
				});
				$(taskPage).find('#userClose').click(function (evt) {
					evt.preventDefault();
					$(taskPage).find('.userWrapper').addClass('not');
				});
				$(taskPage).find('#saveTeam').click(function (event) {
					event.preventDefault();
					const teamForm = $(taskPage).find('#teamForm');
					if (teamForm.valid()) {
						const team = teamForm.toObject();
						console.log(team);
						// Maybe show data
						// Persist the team
						// Hide the input
						$(taskPage).find('.teamWrapper').addClass('not');
					}
				});
				$(taskPage).find('#saveUser').click(function (event) {
					event.preventDefault();
					const userForm = $(taskPage).find('#userForm');
					if (userForm.valid()) {
						const user = userForm.toObject();
						console.log(user);
						// Maybe show data
						// Persist the team
						// Hide the input
						$(taskPage).find('.userWrapper').addClass('not');
					}
				});

				initialised = true;
			}
		},
		/**
		 * 111917kl
		 * modification of the loadTasks method to load tasks retrieved from the server
		 */
		loadServerTasks: function (tasks) {
			$(taskPage).find('#tblTasks tbody').empty();
			$.each(tasks, function (index, task) {
				if (!task.complete) {
					task.complete = false;
				}
				$('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
				taskCountChanged();
				console.log('about to render table with server tasks');
				//renderTable(); --skip for now, this just sets style class for overdue tasks 111917kl
			});
		}

		,
		loadTasks:

			function () {
				$(taskPage).find('#tblTasks tbody').empty();
				storageEngine.findAll('task', function (tasks) {
					tasks.sort(function (o1, o2) {
						return Date.parse(o1.requiredBy).compareTo(Date.parse(o2.requiredBy));
					});

					$.each(tasks, function (index, task) {
						if (!task.complete) {
							task.complete = false;
						}
						$('#taskRow').tmpl(task).appendTo($(taskPage).find('#tblTasks tbody'));
						taskCountChanged();
						renderTable();
					});
				}, errorLogger);
			}
	}

}();
