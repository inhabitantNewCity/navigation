package com.example.vlad.navigation.calculation.machineVisionSystem.filtration;

import android.media.Image;
import android.support.annotation.NonNull;

public class BlurImage implements Comparable {
    private Image image;
    private Double gauseDestribution;

    public BlurImage(Image image) {
        this.image = image;
    }

    @Override
    public int compareTo(@NonNull Object o) {
        if(o instanceof BlurImage) {
            BlurImage image = (BlurImage) o;
            return gauseDestribution.compareTo(image.getGauseDestribution());
        }
        return 0;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Double getGauseDestribution() {
        return gauseDestribution;
    }

    public void setGauseDestribution(Double gauseDestribution) {
        this.gauseDestribution = gauseDestribution;
    }
}
