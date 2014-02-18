package com.Android.games.List_Files;

import java.io.File;
import java.util.ArrayList;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Pixmap.Format;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.List;
import com.badlogic.gdx.scenes.scene2d.ui.List.ListStyle;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane.ScrollPaneStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class Main implements ApplicationListener 
{
	
	private Stage stage;
	private Skin skin;
	
	private List list;

	private Table table;
	private Table exit_table;
	private Table text_table;
	
	private ScrollPane scrollpane;

	private TextButton documents;
	private TextButton music;
	private TextButton images;
	private TextButton videos;
	private TextButton everything;
	private TextButton exit;
	
	private TextButton back;
	
	private TextField text;
	
	private ListStyle liststyle;
	private ScrollPaneStyle scrollpanestyle;
	
	ArrayList<File> list_of_files = new ArrayList<File>();
	
	private float scaling_x = 0;
	private float scaling_y = 0;
	
	private float width = 0;
	private float height = 0;
	
	@Override
	public void create() 
	{		
		width = Gdx.graphics.getWidth();
		height = Gdx.graphics.getHeight();
		
		scaling_x = width/800;
		scaling_y = height/480;
		
		stage = new Stage();
		Gdx.input.setInputProcessor(stage);
		
		skin = new Skin();
		
		Menu_Setup();
		
		/*
		list_of_files = getfile( Gdx.files.external("/").file(), "music");
		String[] test = new String[list_of_files.size()];
		for( int i = 0; i < list_of_files.size(); i++ )
		{
			test[i] = list_of_files.get(i).toString();
		}
		//String[] test = list_of_files.toArray( new String[list_of_files.size()]);
		
		Asset_Setup( test );
		*/
		
	}
	
	private void Menu_Setup()
	{
		//Generate a 1x1 white texture and store it in the skin named "white".
		Pixmap pixmap = new Pixmap(1, 1, Format.RGBA8888);
		pixmap.setColor(Color.WHITE);
		pixmap.fill();
		skin.add("white", new Texture(pixmap));
		
		// Store the default libgdx font under the name "default".
		skin.add( "default", new BitmapFont() );
		
		TextFieldStyle textfieldstyle = new TextFieldStyle();
		textfieldstyle.font = new BitmapFont();
		textfieldstyle.font.scale(( 4 * scaling_x ));
		textfieldstyle.fontColor = new Color( 1, 1, 1, 1 );
		textfieldstyle.selection = skin.newDrawable( "white", Color.DARK_GRAY );
		skin.add( "default", textfieldstyle );
		
		TextButtonStyle textButtonStyle = new TextButtonStyle();
		textButtonStyle.up = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.down = skin.newDrawable("white", Color.DARK_GRAY);
		textButtonStyle.checked = skin.newDrawable("white", Color.BLUE);
		textButtonStyle.over = skin.newDrawable("white", Color.LIGHT_GRAY);
		textButtonStyle.font = skin.getFont("default");
		skin.add("default", textButtonStyle);
		
		table = new Table();
		table.setFillParent(true);
		table.setHeight(( 80 * scaling_y ));
		table.setY(( -40 * scaling_y ));
		
		exit_table = new Table();
		exit_table.setFillParent(true);
		exit_table.setHeight(( 80 * scaling_y ));
		exit_table.setY(( -145 * scaling_y ));
		
		text_table = new Table();
		text_table.setFillParent(true);
		text_table.setHeight(( 100 * scaling_y ));
		text_table.setY(( 145 * scaling_y ));
		text_table.setX(( 40 * scaling_x ));
		
		stage.addActor( table );
		stage.addActor( exit_table );
		stage.addActor( text_table );
		 
		// Create a button with the "default" TextButtonStyle. A 3rd parameter can be used to specify a name other than "default".

		documents = new TextButton( "Documents", skin );
		music = new TextButton( "Music", skin );
		images = new TextButton( "Images", skin );
		videos = new TextButton( "Videos", skin );
		everything = new TextButton( "Everything", skin );
		exit = new TextButton ("Exit", skin );
		
		text = new TextField( "File Lister", skin );
		
		table.add( documents ).pad(( 15* scaling_x )).height(( 80 * scaling_y )).width(( 100 * scaling_x ));
		table.add( music ).pad(( 15* scaling_x )).height(( 80 * scaling_y )).width(( 100 * scaling_x ));
		table.add( images ).pad(( 15* scaling_x )).height(( 80 * scaling_y )).width(( 100 * scaling_x ));
		table.add( videos ).pad(( 15* scaling_x )).height(( 80 * scaling_y )).width(( 100 * scaling_x ));
		table.add( everything ).pad(( 15* scaling_x )).height(( 80 * scaling_y )).width(( 100 * scaling_x ));
		
		exit_table.add( exit ).pad(( 15 * scaling_x )).height(( 80 * scaling_y )).width(( 100 * scaling_x ));
		
		text_table.add( text ).width(( 380*scaling_x ));
		
		// Add a listener to the button. ChangeListener is fired when the button's checked state changes, eg when clicked,
		// Button#setChecked() is called, via a key press, etc. If the event.cancel() is called, the checked state will be reverted.
		// ClickListener could have been used, but would only fire when clicked. Also, canceling a ClickListener event won't
		// revert the checked state.
		
		documents.addListener(new InputListener() 
		{
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		documents.setText("Please Wait\n................");
		 		documents.setColor( new Color( 0, 0, 50, 255 ));
				return true;
				
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		table.clear();
				exit_table.clear();
				text_table.clear();
				
				list_of_files = getfile( Gdx.files.external("/external_sd").file(), "doc" );
				String[] Documents = new String[list_of_files.size()];
				
				for( int i = 0; i < list_of_files.size(); i++ )
				{
					Documents[i] = list_of_files.get(i).toString();
				}

				Asset_Setup( Documents );
				
		 	}
		});
		
		music.addListener(new InputListener() 
		{
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		music.setText("Please Wait\n................");
		 		music.setColor( new Color( 0, 0, 50, 255 ));
				return true;
				
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		table.clear();
				exit_table.clear();
				text_table.clear();
				
				list_of_files = getfile( Gdx.files.external("/").file(), "music");
				String[] Music = new String[list_of_files.size()];
				
				for( int i = 0; i < list_of_files.size(); i++ )
				{
					Music[i] = list_of_files.get(i).toString();
				}

				Asset_Setup( Music );
				
		 	}
		});

		
		images.addListener(new InputListener() 
		{
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		images.setText("Please Wait\n................");
		 		images.setColor( new Color( 0, 0, 50, 255 ));
				return true;
				
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		table.clear();
				exit_table.clear();
				text_table.clear();
				
				list_of_files = getfile( Gdx.files.external("/").file(), "image");
				String[] Images = new String[list_of_files.size()];
				
				for( int i = 0; i < list_of_files.size(); i++ )
				{
					Images[i] = list_of_files.get(i).toString();
				}

				Asset_Setup( Images );
				
		 	}
		});
		
		videos.addListener(new InputListener() 
		{
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		videos.setText("Please Wait\n................");
		 		videos.setColor( new Color( 0, 0, 50, 255 ));
				return true;
				
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		table.clear();
				exit_table.clear();
				text_table.clear();
				
				list_of_files = getfile( Gdx.files.external("/").file(), "video");
				String[] Videos = new String[list_of_files.size()];
				
				for( int i = 0; i < list_of_files.size(); i++ )
				{
					Videos[i] = list_of_files.get(i).toString();
				}

				Asset_Setup( Videos );
				
		 	}
		});
		
		everything.addListener(new InputListener() 
		{
		 	public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		everything.setText("Please Wait\n................");
		 		everything.setColor( new Color( 0, 0, 50, 255 ));
				return true;
				
		 	}
		 
		 	public void touchUp (InputEvent event, float x, float y, int pointer, int button) 
		 	{

		 		table.clear();
				exit_table.clear();
				text_table.clear();
				
				list_of_files = getfile( Gdx.files.external("/").file(), "everything");
				String[] Everything = new String[list_of_files.size()];
				
				for( int i = 0; i < list_of_files.size(); i++ )
				{
					Everything[i] = list_of_files.get(i).toString();
				}

				Asset_Setup( Everything );
				
		 	}
		});

		exit.addListener(new ChangeListener()
		{
			public void changed (ChangeEvent event, Actor actor) 
			{
				Gdx.app.exit();
			}
		});
		
		
		//table.add( scrollpane ).expandY();
	}
	
	ArrayList<File> fileList = new ArrayList<File>();
	public ArrayList<File> getfile(File dir,String fileType)//pass fileType as a music , video, etc.               
	{
	    File listFile[] = dir.listFiles();
	    if (listFile != null && listFile.length > 0) 
	    {
	        for (int i = 0; i < listFile.length; i++) 
	        {

	            if (listFile[i].isDirectory()) 
	            {
	                getfile(listFile[i],fileType);
	            } 
	            else 
	            {
	                if("doc".equals(fileType))
	                {
	                    if(listFile[i].getName().endsWith(".pdf") || listFile[i].getName().endsWith(".txt") || 
	                       listFile[i].getName().endsWith(".xml") || listFile[i].getName().endsWith(".doc") ||
	                       listFile[i].getName().endsWith(".xls") || listFile[i].getName().endsWith(".xlsx"))
	                     {
	                        fileList.add(listFile[i]);  
	                     }
	                }
	                else if("music".equals(fileType))
	                {
	                    if(listFile[i].getName().endsWith(".mp3"))
	                    {
	                        fileList.add(listFile[i]);
	                    }
	                }
	                else if("video".equals(fileType))
	                {
	                    if(listFile[i].getName().endsWith(".mp4"))
	                    {
	                        fileList.add(listFile[i]);
	                    }
	                }
	                else if("image".equals(fileType))
	                {
	                    if(listFile[i].getName().endsWith(".png") || listFile[i].getName().endsWith(".jpg")
	                       || listFile[i].getName().endsWith(".jpeg") || listFile[i].getName().endsWith(".gif"))
	                    {
	                        fileList.add(listFile[i]);
	                    }
	                }
	                else
	                {
	                	fileList.add(listFile[i]);
	                }
	            }
	        }
	    }
	    return fileList;
	}
	
	
	
	private void Asset_Setup( String[] phone_data )
	{
		liststyle = new ListStyle();
		liststyle.font = new BitmapFont();
		liststyle.fontColorSelected = new Color( 1, 1, 1, 1 );
		liststyle.fontColorUnselected = new Color( 1, 1, 1, 1 );
		liststyle.selection = skin.newDrawable("white", Color.DARK_GRAY);
		
		scrollpanestyle = new ScrollPaneStyle();
		scrollpanestyle.hScrollKnob = skin.newDrawable( "white", Color.BLACK );
		scrollpanestyle.vScrollKnob = skin.newDrawable( "white", Color.GRAY );
		
		// Store the default libgdx font under the name "default".
		skin.add( "default", new BitmapFont() );
		skin.add( "default", liststyle );
		skin.add( "default", scrollpanestyle );

		list = new List( phone_data, skin );
		
		scrollpane = new ScrollPane( list, skin );
		scrollpane.setScrollingDisabled(false, false);

		back = new TextButton( "Back", skin );

		table.setHeight(height);
		table.setY(0);
		table.setFillParent(true);
		 
		table.add( scrollpane ).expandY();
		table.add( back ).pad(15).height(80).width(80);

		stage.addActor(table);
		
		back.addListener(new ChangeListener()
		{
			public void changed (ChangeEvent event, Actor actor) 
			{
				table.clear();
				list_of_files.clear();
				Menu_Setup();
			}
		});
	}

	@Override
	public void dispose() 
	{
		stage.dispose();
	}

	@Override
	public void render() 
	{		
		Gdx.gl.glClearColor(0, 0, 0, 0);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		
		stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();

        
	}

	@Override
	public void resize(int width, int height) 
	{
		stage.setViewport(width, height, true);
	}

	@Override
	public void pause() 
	{
	}

	@Override
	public void resume() 
	{
	}
}