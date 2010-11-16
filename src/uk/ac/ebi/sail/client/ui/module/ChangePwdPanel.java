package uk.ac.ebi.sail.client.ui.module;

import com.gwtext.client.core.Position;
import com.gwtext.client.widgets.Button;
import com.gwtext.client.widgets.Panel;
import com.gwtext.client.widgets.Window;
import com.gwtext.client.widgets.event.ButtonListenerAdapter;
import com.gwtext.client.widgets.form.FormPanel;
import com.gwtext.client.widgets.form.TextField;
import com.gwtext.client.widgets.form.ValidationException;
import com.gwtext.client.widgets.form.Validator;
import com.gwtext.client.widgets.grid.GridPanel;
import com.gwtext.client.widgets.grid.RowSelectionModel;
import com.gwtext.client.widgets.grid.event.RowSelectionListenerAdapter;
import com.gwtext.client.widgets.layout.ColumnLayout;
import com.gwtext.client.widgets.layout.FitLayout;
import java.util.ArrayList;
import java.util.Iterator;


import uk.ac.ebi.sail.client.BackendService;
import uk.ac.ebi.sail.client.common.User;
import uk.ac.ebi.sail.client.ui.ConfigPanel;
import uk.ac.ebi.sail.client.ui.module.AdminPanel;


import com.gwtext.client.data.FieldDef;
import com.gwtext.client.data.RecordDef;
import com.gwtext.client.data.Store;
import com.gwtext.client.data.StringFieldDef;
import com.gwtext.client.widgets.MessageBox;
import com.gwtext.client.widgets.PaddedPanel;
import com.gwtext.client.widgets.ToolTip;
import com.gwtext.client.widgets.form.FieldSet;
import com.gwtext.client.widgets.grid.ColumnConfig;
import com.gwtext.client.widgets.grid.ColumnModel;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.gwtext.client.data.*;  
import com.gwtext.client.widgets.layout.ColumnLayoutData;
import com.gwtext.client.core.EventObject;
public class ChangePwdPanel extends Panel {
	static Object[][] input;
	static int collectionId=1;
	GridPanel grid = new GridPanel();
	Panel panel = new Panel();  
	static Button editButton = new Button();
	static Button addButton = new Button();
	static Button deleteButton = new Button();
	private int row=-1;
//	private String btnID;

	public ChangePwdPanel(final String[][] result){
		input=(Object[][])result;
		Panel panel = new Panel();  
        panel.setBorder(false);  
        panel.setPaddings(15);
        panel.setAutoScroll(true);
        
        final FormPanel formPanel = new FormPanel();  
        formPanel.setFrame(true);  
        formPanel.setLabelAlign(Position.LEFT);  
        formPanel.setPaddings(5);  
        formPanel.setAutoHeight(true);
        formPanel.setWidth(700); 
        formPanel.setCollapsible(true);
        
        Panel inner = new Panel();  
        inner.setLayout(new ColumnLayout());
        
        Panel columnOne = new Panel();  
        columnOne.setLayout(new FitLayout());
 
        final RowSelectionModel sm = new RowSelectionModel(true);  
        sm.addListener(new RowSelectionListenerAdapter() {  
            public void onRowSelect(RowSelectionModel sm, int rowIndex, Record record) {  
                formPanel.getForm().loadRecord(record);
                row=rowIndex;
//                System.out.println("RowIndex "+ rowIndex+""+result[rowIndex][0]);
            }  
        }); 
        
        
      //  Button button = new Button("Set Icon");  
      //  formPanel.add(button);
        inner.add(columnOne, new ColumnLayoutData(0.6)); 
        
        FieldSet fieldSet = new FieldSet();  
        fieldSet.setLabelWidth(90);  
        fieldSet.setTitle("User Details");  
        fieldSet.setAutoHeight(true);  
        fieldSet.setBorder(false);  
  
        //the field names must match the data field values from the Store  
       // fieldSet.add(new TextField("ID", "id", 120));  
        fieldSet.add(new TextField("First Name", "fname", 120));  
        fieldSet.add(new TextField("Last Name", "lname", 120));  
        fieldSet.add(new TextField("User Name*", "uname", 120));
        //fieldSet.add(new TextField("Password*", "pwd", 120));
       // fieldSet.add(new TextField("confirm Password", "cpwd", 120));
        fieldSet.add(new TextField("Email", "mail", 120));
        fieldSet.add(new TextField("Phone", "phone", 120));
        fieldSet.add(new TextField("Description", "Description", 120));
        
      
        
        inner.add(new PaddedPanel(fieldSet, 0, 10, 0, 0), new ColumnLayoutData(0.4));  
        
        //create another FieldSet  
        FieldSet detailsFS = new FieldSet("Change Password");  
        detailsFS.setCollapsible(true);  
        detailsFS.setAutoHeight(true);  
        
        //add fields to Details FieldSet
        final TextField oldPwd = new TextField("Old Password", "oldPwd", 210);  
        oldPwd.setPassword(true);
        detailsFS.add(oldPwd);  
        
        
        System.out.println("Mein Password ist"+AdminPanel.user.getPwd()+" "+AdminPanel.user.getUsername());
        
        final TextField pwd=new TextField("New Password*", "pwd", 210);
	    pwd.setPassword(true);
	    detailsFS.add(pwd);
	    
	    final TextField cPwd=new TextField("confirm Password*", "cPwd", 210);
	    cPwd.setPassword(true);
	    cPwd.setValidator(new Validator(){
	    	public boolean validate(String value) throws ValidationException {
	    		if(cPwd.getText().contentEquals(pwd.getText())) {
					return true;
				} else {
					throw new ValidationException("Passwords dont match. Please try again.");
				}
	    	}
	    });

	    cPwd.setPassword(true);
	    detailsFS.add(cPwd);
	    
	    final FormPanel mailForm=new FormPanel();
        mailForm.setFrame(true);  
        mailForm.setLabelAlign(Position.LEFT);  
        mailForm.setPaddings(5);  
        mailForm.setWidth(700);
        mailForm.setAutoHeight(true);
        
        detailsFS.addButton(new Button("Change",new ButtonListenerAdapter(){
        	@Override
        	public void onClick(Button button, EventObject e) {
//        	    BackendService.Util.getInstance().changePwd(ConfigPanel.transform(mailForm.getForm().getValues()),,new AsyncCallback<Void>(){
        		BackendService.Util.getInstance().changePwd(oldPwd.getText(),cPwd.getText(),AdminPanel.user,new AsyncCallback<Boolean>(){
        	    	@Override
        			public void onFailure(Throwable caught) {}
        			@Override
        			public void onSuccess(Boolean result) {
        				if(result){MessageBox.alert("Success","Password successfully changed");
        				    oldPwd.setValue("");
        				    pwd.setValue("");
        				    cPwd.setValue("");
        				    doLayout();
        				}
        				else {MessageBox.alert("Failure","Old Password doesn`t match. Please,try again");}
        			}
        	    });
        	}
        }));
        
//   "    if(AdminPanel.role.equalsIgnoreCase("ADMIN"))
        System.out.println(" Die role ist wann wo "+AdminPanel.user.getRole());
        mailForm.add(detailsFS);

        RecordDef recordDef = new RecordDef(  
                new FieldDef[]{  
                        new StringFieldDef("id"),  
                        new StringFieldDef("fname"),  
                        new StringFieldDef("lname"),  
                        new StringFieldDef("uname"),  
                   //     new StringFieldDef("pwd"),
                        new StringFieldDef("mail"),  
                        new StringFieldDef("phone"),
                        new StringFieldDef("Description"),
                        new StringFieldDef("role")
                }  
        );  
  
        GridPanel grid = new GridPanel();  
        
        Object[][] data = input;  
        MemoryProxy proxy = new MemoryProxy(data);  
  
        final ArrayReader reader = new ArrayReader(recordDef);  
        final Store store = new Store(proxy, reader);  
        
        store.load();  
        grid.setStore(store);
        grid.setSelectionModel(sm); 
        
        columnOne.add(grid);  
        ColumnConfig[] columns = new ColumnConfig[]{  
                new ColumnConfig("ID", "id", 35, true, null, "id"),  
                new ColumnConfig("First Name", "fname", 65),  
                new ColumnConfig("Last Name", "lname", 65),  
                new ColumnConfig("User Name", "uname", 65),  
                //new ColumnConfig("Password", "pwd", 65), 
                new ColumnConfig("Email", "mail", 60, true), 
                new ColumnConfig("Phone", "phone", 60, true), 
                new ColumnConfig("Description", "desc", 60, true), 
                new ColumnConfig("Role", "role", 60, true)
        };  
  
        ColumnModel columnModel = new ColumnModel(columns);  
        grid.setColumnModel(columnModel);  
  
        grid.setFrame(true);  
        grid.setStripeRows(true);  
        grid.setAutoExpandColumn("id");  
  
        grid.setHeight(250);  
        grid.setWidth(700);
        grid.setTitle("User"); 
        
        addButton = new Button("Add", new ButtonListenerAdapter() {  
            public void onClick(Button button, EventObject e) { 
           	    store.removeAll();
           	    editButton.disable();
           	    addButton.disable();
           	    deleteButton.disable();
           	    Object[][] dataTemp = addUser(ConfigPanel.transform(formPanel.getForm().getValues()));  
                final MemoryProxy proxyTemp = new MemoryProxy(dataTemp);  
                Store temp = new Store(proxyTemp, reader);
                temp.load();
                Record rs[] = temp.getRecords();
                ChangePwdPanel.input=dataTemp;
                store.add(rs);
                temp.removeAll();
           
                store.commitChanges();
            }  
        });
        addButton.disable();
        inner.addButton(addButton);
        
        editButton = new Button("Edit", new ButtonListenerAdapter() {  
             public void onClick(Button button, EventObject e) { 
           	    if(row==-1){
           	    	MessageBox.alert("Select a row which should be edit.");
           	    }
           	    else {
            	    store.removeAll(); 
           	        editButton.disable();
        	        addButton.disable();
        	        deleteButton.disable();
           	        Object[][] dataTemp =editUser(ConfigPanel.transform(formPanel.getForm().getValues()),row);
                    final MemoryProxy proxyTemp = new MemoryProxy(dataTemp);  
                    Store temp = new Store(proxyTemp, reader);
                    temp.load();
                    Record rs[] = temp.getRecords();
                    ChangePwdPanel.input=dataTemp;
                    store.add(rs);
                    temp.removeAll();
                    store.commitChanges();
           	    }
            } 
            
        });
        inner.addButton(editButton);
        
        
         deleteButton = new Button("Delete", new ButtonListenerAdapter() {  
            public void onClick(Button button, EventObject e) { 
           	   try {
           	       Object[][] dataTemp = arrayDel((String[][])input, row);
           	       store.removeAll(); 
           	       editButton.disable();
            	   addButton.disable();
            	   deleteButton.disable();
                   final MemoryProxy proxyTemp = new MemoryProxy(dataTemp);  
                   Store temp = new Store(proxyTemp, reader);
                   temp.load();
                   Record rs[] = temp.getRecords();
                   ChangePwdPanel.input=dataTemp;
                   store.add(rs);
                   temp.removeAll();
                   store.commitChanges();
               
               } catch (Exception e2) {
		    	   MessageBox.alert("You have to select a user!");
               }
               editButton.enable();
        	   addButton.enable();
        	   deleteButton.enable();
               
               
            }  
        });
        inner.addButton(deleteButton);
        formPanel.add(inner);

        panel.add(formPanel);  
        panel.add(mailForm);
        add(panel);  
        
    }
	public String[][] arrayDel(String[][] array,int rown){
		ArrayList<String[]> list=new ArrayList<String[]>();
		BackendService.Util.getInstance().delObject(Integer.parseInt(array[rown][0]), new AsyncCallback<Boolean>() {
			@Override
			    public void onFailure(Throwable caught) {
				    editButton.enable();
           	        addButton.enable();
           	        deleteButton.enable();
           	    }
			@Override
			    public void onSuccess(Boolean result) {
				    editButton.enable();
           	        addButton.enable();
           	        deleteButton.enable();
			
			
			}
			
		});
		for(int i=0; i<array.length; i++){
		    String [] rowVal=new String[9];
		    for(int x=0;x<9;x++){
			    rowVal[x]=array[i][x];
		    }
		    list.add(rowVal);
		}
		list.remove(rown);
		String[][] result=convertArrayListToArray(list);
		return result;
	}
	
	
	
	public String[][] arrayEdit(String[][] arrays, String[] array,int nRow){
		ArrayList<String[]> list=new ArrayList<String[]>();
		for(int i=0; i<arrays.length; i++){
			String [] rowVal=new String[9];
			for(int x=0;x<9;x++){
				rowVal[x]=arrays[i][x];
			}
			list.add(rowVal);
		}
		list.remove(nRow);
		list.add(nRow,array);
		String[][] result=convertArrayListToArray(list);
		return result;
	}
	
	public String[][] arrayAdd(String[][] arrays, String[] array){
		ArrayList<String[]> list=new ArrayList<String[]>();
		int maxId=0;
		for(int i=0; i<arrays.length; i++){
			String [] rowVal=new String[9];
			if(Integer.parseInt(arrays[i][0])>maxId){
				maxId=Integer.parseInt(arrays[i][0]);
			}
		    for(int x=0;x<9;x++){
				rowVal[x]=arrays[i][x];
			}
			list.add(rowVal);
		}
		array[0]=Integer.toString(maxId+1);
		ChangePwdPanel.collectionId++;
		list.add(array);
		String[][] result=convertArrayListToArray(list);
		return result;
	}
	
	public String[][] convertArrayListToArray( ArrayList<String[]> list )
    {
		Iterator<String[]> itr = list.iterator();
	    int i=0;
	    String[][] result=new String[list.size()][10];
	    while (itr.hasNext()) {
	      String[] element = itr.next();
	      for(int x=0;x<9;x++){
	    	  result[i][x]=element[x];
	      }
	      i++;
	    }
	    return result;
    }
	
	
	public String[][] editUser(String values,int row){
		String [][] output=(String[][])ChangePwdPanel.input;
		String newValues="id="+output[row][0]+"&"+values;
		BackendService.Util.getInstance().editUser(newValues,"edit", new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				editButton.enable();
           	    addButton.enable();
           	    deleteButton.enable();
				
			}
			@Override
			public void onSuccess(Void result) {
				editButton.enable();
           	    addButton.enable();
           	    deleteButton.enable();
			}
		});
		
		
		String[] array = newValues.split("&");
		for(int i=0;i<array.length;i++){
			String[] value=array[i].split("=");
			if(value.length==1){
				output[row][i]="";
			}
			else{
			    output[row][i]=value[1];
			}
		}
		
		return output ;
	}
	
	public String[][] addUser(String values){
		String[] array = values.split("&");
		String newValues;
		String [] add =new String[9];
		for(int i=0;i<array.length;i++){
			String[] value=array[i].split("=");
			if(value.length==1){add[i+1]="";}
			else{add[i+1]=value[1];}
		}
		String [][] output=arrayAdd((String[][])ChangePwdPanel.input,add);
        newValues=values;
		BackendService.Util.getInstance().addUser(newValues, new AsyncCallback<Void>() {
			@Override
			public void onFailure(Throwable caught) {
				 System.out.println("mist");
				 editButton.enable();
	       	     addButton.enable();
	           	 deleteButton.enable();
			}
			@Override
			public void onSuccess(Void result) {
				editButton.enable();
           	    addButton.enable();
           	    deleteButton.enable();
			}
			
		});
		return output;
	}
	
	public static void addUserToCollection(final int collectionId){
		final Window window = new Window(); 
		window.setTitle("Add User");
		final FormPanel formPanel = new FormPanel();  
		formPanel.setFrame(true);  
		formPanel.setWidth(350);  
		formPanel.setLabelWidth(75);  
		
		TextField uname = new TextField("User Name", "uname", 230);  
		uname.setAllowBlank(false);  
		uname.setValidator(new Validator() {
			@Override
			public boolean validate(String value) throws ValidationException {
				if (AdminPanel.username.contains(value)) {
					return true;
				}
				else 
					throw new ValidationException("Username doesn`t exist!");
			}
		});
		formPanel.add(uname);
				
		Button save = new Button("Save", new ButtonListenerAdapter(){
			@Override
			public void onClick(Button button, EventObject e) {
			    final String [] user=ConfigPanel.transform(formPanel.getForm().getValues()).split("=");
			    int userIndexId = AdminPanel.username.indexOf(user[1]);
			    String userId=AdminPanel.userid.get(userIndexId);
			    System.out.println(userId+" "+collectionId);
			    BackendService.Util.getInstance().addUserToColl(userId, Integer.toString(collectionId), new AsyncCallback<Void>() {
					@Override
					public void onFailure(Throwable caught) {}
					@Override
					public void onSuccess(Void result) {
						MessageBox.alert("Success","User "+user[1]+" succesfully added!");
						window.close();
					}
				});
			    
			}
		});  
		formPanel.addButton(save);  
		
		Button cancel = new Button("Cancel",new ButtonListenerAdapter(){
			@Override
			public void onClick(Button button, EventObject e) {
				window.close();
			}
		});  
		formPanel.addButton(cancel);  
		   
		window.add(formPanel);  
		window.show();
		}

	
	
//	
//	final Window cPwdWindow = new Window();
//	
//	public void ChangePwdPanel2(){
//	    cPwdWindow.setTitle("Change Password");
//	    cPwdWindow.setPaddings(5);
//	    cPwdWindow.setClosable(true);
//	    
//	    final Panel panel = new Panel();  
//		panel.setAutoHeight(true);
//		panel.setPaddings(5);
//		
//		final FormPanel form=new FormPanel();
//		form.setPaddings(5);
//	    form.add(new TextField("User Name"));
//	    
//	    form.add(new TextField("Old Password"));
//	    
//	    final TextField password=new TextField("New Password*", "password", 190);
//	    password.setPassword(true);
//
//		final TextField cpassword=new TextField("confirm Password*", "cpassword", 190);
//	    cpassword.setPassword(true);
//	    cpassword.setValidator(new Validator(){
//	    	public boolean validate(String value) throws ValidationException {
//	    		if(cpassword.getText().contentEquals(password.getText())) {
//					return true;
//				} else {
//					throw new ValidationException("Password or username dont match. Please try again.");
//				}
//	    	}
//	    });
//	    
//	    
//	    
//	
//	}
	
}
