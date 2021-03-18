package com.merchpandas.wallpicx.Model;


public class ExploreItem {
    public String name;
    public String imageLink;
    public int collectionId;

    public ExploreItem() {
    }

    public ExploreItem(String imageLink, int collectionId) {
        this.name = name;
        this.collectionId = collectionId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        this.imageLink = imageLink;
    }

    public int getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(int collectionId) {
        this.collectionId = collectionId;
    }
}
