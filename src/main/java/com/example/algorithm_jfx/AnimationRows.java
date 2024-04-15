package com.example.algorithm_jfx;

import javafx.animation.TranslateTransition;
import javafx.scene.control.TableCell;
import javafx.util.Duration;

public class AnimationRows<T> extends TableCell<T, Object> {



        private TranslateTransition translateTransition;

        public AnimationRows() {
            this.translateTransition = new TranslateTransition(Duration.seconds(1), this);
            this.translateTransition.setFromX(0);
            this.translateTransition.setToX(0);
        }

        @Override
        protected void updateItem(Object item, boolean empty) {
            super.updateItem(item, empty);
            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                setText(item.toString());
                animate();
            }
        }

        private void animate() {
            translateTransition.stop();
            translateTransition.setFromX(-10);
            translateTransition.setToX(0);
            translateTransition.play();
        }
    }

