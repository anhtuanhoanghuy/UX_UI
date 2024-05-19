package com.nftapp.nftmarketplace.model;

import android.graphics.Bitmap;
import android.graphics.Point;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

public class QuizzPackage implements Serializable {
    public int id;

    public String package_number;

    public QuizzPackage(int id, String package_number) {
        this.id = id;
        this.package_number = package_number;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPackage_number() {
        return package_number;
    }

    public void setPackage_number(String package_number) {
        this.package_number = package_number;
    }

    public static class FloodFill {

        public static void floodFill(Bitmap bitmap, Point point, int targetColor, int newColor){

            int width = bitmap.getWidth();
            int height = bitmap.getHeight();

            if(targetColor != newColor){

                Queue<Point> queue =  new LinkedList<>();   // see carefully it's point not paint

                do {
                    int x = point.x;
                    int y = point.y;

                    while (x > 0 && bitmap.getPixel(x - 1, y) == targetColor) {
                        x--;
                    }

                    boolean spanUp = false;
                    boolean spanDown = false;

                    while (x < width && bitmap.getPixel(x, y) == targetColor) {
                        bitmap.setPixel(x, y, newColor);

                        if (!spanUp && y > 0 && bitmap.getPixel(x, y - 1) == targetColor) {
                            queue.add(new Point(x, y - 1));
                            spanUp = true;
                        } else if (!spanUp && y > 0 && bitmap.getPixel(x, y - 1) != targetColor) {
                            spanUp = false;
                        }
                        if (!spanDown && y < height - 1 && bitmap.getPixel(x, y + 1) == targetColor) {
                            queue.add(new Point(x, y + 1));
                            spanDown = true;
                        } else if (!spanDown && y < height - 1 && bitmap.getPixel(x, y + 1) != targetColor) {
                            spanDown = false;
                        }
                        x++;
                    }
                }while ((point=queue.poll())!=null);
            }
        }
    }
}
