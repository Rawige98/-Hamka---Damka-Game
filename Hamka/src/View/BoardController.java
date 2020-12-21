package View;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;


public class BoardController implements Initializable  {
	
	
	private final Logger log = Logger.getLogger("BoardController");

	
    @FXML
    private Circle p1_1;

    @FXML
    private Pane tile20;

    @FXML
    private Pane tile64;

    @FXML
    private Pane tile21;

    @FXML
    private Pane tile65;

    @FXML
    private Pane tile22;

    @FXML
    private Pane tile66;

    @FXML
    private Pane tile23;

    @FXML
    private Pane tile67;

    @FXML
    private Pane tile60;

    @FXML
    private Circle p1_5;

    @FXML
    private Circle p1_4;

    @FXML
    private Pane tile61;

    @FXML
    private Circle p1_3;

    @FXML
    private Pane tile62;

    @FXML
    private Circle p1_2;

    @FXML
    private Pane tile63;

    @FXML
    private Circle p1_9;

    @FXML
    private Circle p1_8;

    @FXML
    private Circle p1_7;

    @FXML
    private Circle p1_6;

    @FXML
    private Circle p1_10;

    @FXML
    private Pane tile24;

    @FXML
    private Pane tile25;

    @FXML
    private Circle p1_12;

    @FXML
    private Pane tile26;

    @FXML
    private Circle p1_11;

    @FXML
    private Pane tile27;

    @FXML
    private Pane tile53;

    @FXML
    private Pane tile10;

    @FXML
    private Pane tile54;

    @FXML
    private Pane tile11;

    @FXML
    private Pane tile55;

    @FXML
    private Pane tile12;

    @FXML
    private Pane tile56;

    @FXML
    private Pane tile50;

    @FXML
    private Pane tile51;

    @FXML
    private Pane tile52;

    @FXML
    private Pane tile17;

    @FXML
    private Pane tile13;

    @FXML
    private Pane tile57;

    @FXML
    private Pane tile14;

    @FXML
    private Pane tile15;

    @FXML
    private Pane tile16;

    @FXML
    private Pane tile42;

    @FXML
    private Pane tile43;

    @FXML
    private Pane tile00;

    @FXML
    private Pane tile44;

    @FXML
    private Pane tile01;

    @FXML
    private Pane tile45;

    @FXML
    private Circle p2_4;

    @FXML
    private Circle p2_3;

    @FXML
    private Pane tile40;

    @FXML
    private Circle p2_2;

    @FXML
    private Pane tile41;

    @FXML
    private Circle p2_1;

    @FXML
    private Circle p2_8;

    @FXML
    private Circle p2_7;

    @FXML
    private Circle p2_6;

    @FXML
    private Circle p2_5;

    @FXML
    private Circle p2_9;

    @FXML
    private Pane tile06;

    @FXML
    private Pane tile07;

    @FXML
    private Pane tile02;

    @FXML
    private Pane tile46;

    @FXML
    private Pane tile03;

    @FXML
    private Pane tile47;

    @FXML
    private Pane tile04;

    @FXML
    private Pane tile05;

    @FXML
    private Pane tile31;

    @FXML
    private Pane tile75;

    @FXML
    private Pane tile32;

    @FXML
    private Pane tile76;

    @FXML
    private Pane tile33;

    @FXML
    private Pane tile77;

    @FXML
    private Pane tile34;

    @FXML
    private Pane tile71;

    @FXML
    private Pane tile72;

    @FXML
    private Pane tile73;

    @FXML
    private Pane tile30;

    @FXML
    private Pane tile74;

    @FXML
    private Pane tile70;

    @FXML
    private Pane tile35;

    @FXML
    private Circle p2_12;

    @FXML
    private Pane tile36;

    @FXML
    private GridPane board;

    @FXML
    private Circle p2_10;

    @FXML
    private Pane tile37;

    @FXML
    private Circle p2_11;
    
    @FXML
    private Circle leavingCircle;

    
	private final InnerShadow shadow = new InnerShadow();
    private Point2D offset = new Point2D(0.0d, 0.0d);
	private boolean movingPiece = false;
	private final List<Pane> panes = new ArrayList<>();
	private Pane currPane;

    

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		
		
		
		board.addEventFilter(MouseEvent.MOUSE_EXITED, this::leaveBoard);
		board.addEventFilter(MouseEvent.MOUSE_RELEASED, this::checkReleaseOutOfBoard);
		
	}
	
	
	
	public void checkReleaseOutOfBoard(MouseEvent evt) { 
		Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
		if( !inBoard(mousePoint_s) ) {
			leaveBoard(evt);
			evt.consume();
		}
	}

	
	public void leaveBoard(MouseEvent evt) {
		if( movingPiece ) {
			
			final Timeline timeline = new Timeline();

			offset = new Point2D(0.0d, 0.0d);
			movingPiece = false;

			timeline.getKeyFrames().add(
					new KeyFrame(Duration.millis(200), 
							new KeyValue(p1_2.layoutXProperty(), leavingCircle.getLayoutX()),
							new KeyValue(p1_2.layoutYProperty(), leavingCircle.getLayoutY()),
							new KeyValue(p1_2.opacityProperty(), 1.0d),
							new KeyValue(leavingCircle.opacityProperty(), 0.0d)
							)
					);
			timeline.play();			
		}
	}
	
	@FXML
	public void startMovingPiece(MouseEvent evt) { 
		p1_1.setOpacity(0.4d);
		offset = new Point2D(evt.getX(), evt.getY());

		leavingCircle.setOpacity(1.0d);		
		leavingCircle.setLayoutX( p1_2.getLayoutX() );
		leavingCircle.setLayoutY( p1_2.getLayoutY() );
		
		movingPiece = true;
	}
	
	
	@FXML
	public void movePiece(MouseEvent evt) {		
		
		Point2D mousePoint = new Point2D(evt.getX(), evt.getY());		
		Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
		
		if( !inBoard(mousePoint_s) ) {
			return;  // don't relocate() b/c will resize Pane
		}
		
		Point2D mousePoint_p = p1_2.localToParent(mousePoint);		
		p1_2.relocate(mousePoint_p.getX()-offset.getX(), mousePoint_p.getY()-offset.getY());
	}

	
	
	@FXML
	public void finishMovingPiece(MouseEvent evt) {
		
		offset = new Point2D(0.0d, 0.0d);
		
		Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
		Point2D mousePointScene = p1_2.localToScene(mousePoint);
		
		Pane r = pickRectangle( mousePointScene.getX(), mousePointScene.getY() );

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);

		if( r != null ) {
			if( log.isLoggable(Level.FINE) ) {
				log.fine("[FINISH] r=" + r.getId());
			}
			
			Point2D rectScene =r.localToScene(r.getLayoutX(), r.getLayoutY());			
			Point2D parent = board.sceneToLocal(rectScene.getX(), rectScene.getY());
			
			timeline.getKeyFrames().add(
					new KeyFrame(Duration.millis(100), 
							new KeyValue(p1_2.layoutXProperty(), parent.getX()),
							new KeyValue(p1_2.layoutYProperty(), parent.getY()),
							new KeyValue(p1_2.opacityProperty(), 1.0d),
							new KeyValue(leavingCircle.opacityProperty(), 0.0d)
							)
					);

		} else {

			timeline.getKeyFrames().add(
					new KeyFrame(Duration.millis(100), 
							new KeyValue(p1_2.opacityProperty(), 1.0d),
							new KeyValue(leavingCircle.opacityProperty(), 0.0d)
							)
					);
		}
		
		timeline.play();
			
		movingPiece = false;
	}

	public void mouseMoved(MouseEvent evt) {

		Pane p = pickPane(evt);	
		
		if( p == null ) {

			if( currPane != null ) {
				// deselect previous
				currPane.setEffect( null );
			}

			currPane = null;
			return;  // might be out of area but w/i scene
		}
		
		if( p != currPane ) {
			
			if( currPane != null ) {
				// deselect previous
				currPane.setEffect( null );
			}
			
			currPane = p;

			if( log.isLoggable( Level.FINE ) ) {
				log.fine("[MOVED] in " + currPane.getId());
			}

			if( currPane != null ) {  // new selection
				currPane.setEffect( shadow );
			}
		}
	}

	
	
	
	private boolean inBoard(Point2D pt) {	
		Point2D panePt = board.sceneToLocal(pt);
		return panePt.getX()-offset.getX() >= 0.0d 
				&& panePt.getY()-offset.getY() >= 0.0d 
				&& panePt.getX() <= board.getWidth() 
				&& panePt.getY() <= board.getHeight();
	}
	
	
	private Pane pickPane(MouseEvent evt) {
		return pickRectangle(evt.getSceneX(), evt.getSceneY());
	}
	
	private Pane pickRectangle(double sceneX, double sceneY) {
		Pane pickedRectangle = null;
		for( Pane row : panes ) {
			
			//
			// getX/Y == getSceneX/Y because handler registerd on Scene and
			// not node
			//
			
			Point2D mousePoint = new Point2D(sceneX, sceneY);
			Point2D mpLocal = row.sceneToLocal(mousePoint);
			
			if( row.contains(mpLocal) ) {
				if( log.isLoggable(Level.FINEST) ) {
					log.finest("[PICK] selected row=" + row.getId());
				}
				
				for( Node cell : row.getChildrenUnmodifiable() ) {
					
					Point2D mpLocalCell = cell.sceneToLocal(mousePoint);

					if( cell.contains(mpLocalCell) ) {
						if( log.isLoggable(Level.FINEST) ) {
							log.finest("[PICK] selected cell=" + cell.getId());
						}
						pickedRectangle = (Pane)cell;
						break;
					}
				}				
				break;
			}
		}
		return pickedRectangle;
	}
	
	
	
}
