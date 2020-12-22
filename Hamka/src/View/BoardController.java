package View;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

public class BoardController {

	private final Logger log = Logger.getLogger("BoardController");
	private final List<Pane> panes = new ArrayList<>();

	
    @FXML
    private Rectangle t1_7;

    @FXML
    private Rectangle t3_5;

    @FXML
    private Rectangle t5_3;

    @FXML
    private Rectangle t7_1;

    @FXML
    private Rectangle t1_6;

    @FXML
    private Rectangle t3_4;

    @FXML
    private Rectangle t5_2;

    @FXML
    private Rectangle t7_0;

    @FXML
    private Circle leavingCircle;

    @FXML
    private Rectangle t3_7;

    @FXML
    private Rectangle t5_5;

    @FXML
    private Rectangle t7_3;

    @FXML
    private Rectangle t3_6;

    @FXML
    private Rectangle t5_4;

    @FXML
    private Rectangle t7_2;

    @FXML
    private Rectangle t1_3;

    @FXML
    private Rectangle t3_1;

    @FXML
    private Rectangle t1_2;

    @FXML
    private Rectangle t3_0;

    @FXML
    private Rectangle t1_5;

    @FXML
    private Rectangle t3_3;

    @FXML
    private Rectangle t5_1;

    @FXML
    private Rectangle t1_4;

    @FXML
    private Rectangle t3_2;

    @FXML
    private Rectangle t5_0;

    @FXML
    private Rectangle t5_7;

    @FXML
    private Rectangle t7_5;

    @FXML
    private Rectangle t5_6;

    @FXML
    private Rectangle t7_4;

    @FXML
    private Rectangle t7_7;

    @FXML
    private Rectangle t7_6;

    @FXML
    private Rectangle t1_1;

    @FXML
    private Rectangle t1_0;

    @FXML
    private Pane row1;

    @FXML
    private Pane row0;

    @FXML
    private Pane row7;

    @FXML
    private Pane row6;

    @FXML
    private Pane row3;

    @FXML
    private Pane row2;

    @FXML
    private Pane row5;

    @FXML
    private Pane row4;

    @FXML
    private Circle circle;

    @FXML
    private Rectangle t2_6;

    @FXML
    private Rectangle t4_4;

    @FXML
    private Rectangle t6_2;

    @FXML
    private Rectangle t0_7;

    @FXML
    private Rectangle t2_5;

    @FXML
    private Rectangle t4_3;

    @FXML
    private Rectangle t6_1;

    @FXML
    private Rectangle t4_6;

    @FXML
    private Rectangle t6_4;

    @FXML
    private Rectangle t2_7;

    @FXML
    private Rectangle t4_5;

    @FXML
    private Rectangle t6_3;

    @FXML
    private Rectangle t0_4;

    @FXML
    private Rectangle t2_2;

    @FXML
    private Rectangle t4_0;

    @FXML
    private Rectangle t0_3;

    @FXML
    private Rectangle t2_1;

    @FXML
    private Rectangle t0_6;

    @FXML
    private Rectangle t2_4;

    @FXML
    private Rectangle t4_2;

    @FXML
    private Rectangle t6_0;

    @FXML
    private Rectangle t0_5;

    @FXML
    private Rectangle t2_3;

    @FXML
    private Rectangle t4_1;

    @FXML
    private Rectangle t6_6;

    @FXML
    private Rectangle t4_7;

    @FXML
    private Rectangle t6_5;

    @FXML
    private Rectangle t6_7;

    @FXML
    private Rectangle t0_0;

    @FXML
    private Rectangle t0_2;

    @FXML
    private Rectangle t2_0;

    @FXML
    private Rectangle t0_1;

    @FXML
    private Group rectangleGroup;
    
    @FXML
    private Pane boardPane;
    
    private Point2D offset = new Point2D(0.0d, 0.0d);
	private boolean movingPiece = false;

    @FXML
    public void movePiece(MouseEvent evt) {
    	Point2D mousePoint = new Point2D(evt.getX(), evt.getY());		
		Point2D mousePoint_s = new Point2D(evt.getSceneX(), evt.getSceneY());
		
		if( !inBoard(mousePoint_s) ) {
			return;  // don't relocate() b/c will resize Pane
		}
		
		Point2D mousePoint_p = circle.localToParent(mousePoint);		
		circle.relocate(mousePoint_p.getX()-offset.getX(), mousePoint_p.getY()-offset.getY());
    }

    private boolean inBoard(Point2D pt) {	
		Point2D panePt = boardPane.sceneToLocal(pt);
		return panePt.getX()-offset.getX() >= 0.0d 
				&& panePt.getY()-offset.getY() >= 0.0d 
				&& panePt.getX() <= boardPane.getWidth() 
				&& panePt.getY() <= boardPane.getHeight();
	}
    
    @FXML
    public void startMovingPiece(MouseEvent evt) {
    	circle.setOpacity(0.4d);
		offset = new Point2D(evt.getX(), evt.getY());

		leavingCircle.setOpacity(1.0d);		
		leavingCircle.setLayoutX( circle.getLayoutX() );
		leavingCircle.setLayoutY( circle.getLayoutY() );
		
		movingPiece = true;
    }

    @FXML
    public void finishMovingPiece(MouseEvent evt) {
		offset = new Point2D(0.0d, 0.0d);
		
		Point2D mousePoint = new Point2D(evt.getX(), evt.getY());
		Point2D mousePointScene = circle.localToScene(mousePoint);
		
		Rectangle r = pickRectangle( mousePointScene.getX(), mousePointScene.getY() );

		final Timeline timeline = new Timeline();
		timeline.setCycleCount(1);
		timeline.setAutoReverse(false);

		if( r != null ) {
			if( log.isLoggable(Level.FINE) ) {
				log.fine("[FINISH] r=" + r.getId());
			}
			
			Point2D rectScene =r.localToScene(r.getX(), r.getY());			
			Point2D parent = boardPane.sceneToLocal(rectScene.getX(), rectScene.getY());
			
			timeline.getKeyFrames().add(
					new KeyFrame(Duration.millis(100), 
							new KeyValue(circle.layoutXProperty(), parent.getX()),
							new KeyValue(circle.layoutYProperty(), parent.getY()),
							new KeyValue(circle.opacityProperty(), 1.0d),
							new KeyValue(leavingCircle.opacityProperty(), 0.0d)
							)
					);

		} else {

			timeline.getKeyFrames().add(
					new KeyFrame(Duration.millis(100), 
							new KeyValue(circle.opacityProperty(), 1.0d),
							new KeyValue(leavingCircle.opacityProperty(), 0.0d)
							)
					);
		}
		
		timeline.play();
			
		movingPiece = false;
    }

    private Rectangle pickRectangle(MouseEvent evt) {
		return pickRectangle(evt.getSceneX(), evt.getSceneY());
	}
	
	private Rectangle pickRectangle(double sceneX, double sceneY) {
		Rectangle pickedRectangle = null;
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
						pickedRectangle = (Rectangle)cell;
						break;
					}
				}				
				break;
			}
		}
		return pickedRectangle;
	}

    
}
