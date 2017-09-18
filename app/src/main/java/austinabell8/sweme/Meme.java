package austinabell8.sweme;

/**
 * Created by austi on 2017-09-18.
 */

public class Meme {

    private int imageResource;
    private String description;

    public Meme (int imageResource, String description){
        this.imageResource = imageResource;
        this.description = description;
    }

    public int getImageResource() {
        return imageResource;
    }

    public void setImageResource(int imageResource) {
        this.imageResource = imageResource;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
