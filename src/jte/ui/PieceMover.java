/*
 * (C) Frank Migliorino 2014
 * CSE 219 Final Project
 * Portions Copyright Paul Fodor
 */
package jte.ui;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Point2D;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.util.Duration;


/**
 *
 * @author Frank
 */
public class PieceMover {
    public static Timeline getTimeline(ImageView object, ScrollPane scroller, Duration dur , Point2D from, Point2D to, Point2D scrollFrom, Point2D scrollTo) {
        final Timeline build = new Timeline();
        KeyFrame objectX, objectY, scrollX, scrollY;
        return null;
        //objectX = new KeyFrame(dur, new KeyValue(object.translateXProperty(), ))
    }
}
