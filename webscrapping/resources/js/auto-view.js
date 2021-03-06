var AutoView = {
	
	data:{
		items:[],
		counter:0		
	},
	methods:{
		fetchAllAutoNames: function()
		{
					var data = this.$data;
				    var options = {
		            		  headers: {'Content-Type': 'application/json'}
		            		};
					data.counter++;
		            var url = "/api/fetch/names";            
		            axios.get(url,options)
		            .then(function(response) {
						console.log(response);
		            	data.items = response.data;
		            })
		            .catch(this.handleError)
		            .then(function(response) {
		            	data.counter--;
		            });
		}
		
	},
	mounted: function(){
		var me = this;
		me.fetchAllAutoNames();
	},
	created: function()
	{
		
	}
	
	
};