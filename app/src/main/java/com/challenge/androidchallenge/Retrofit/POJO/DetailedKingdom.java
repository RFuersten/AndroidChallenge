
package com.challenge.androidchallenge.Retrofit.POJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class DetailedKingdom {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("climate")
    @Expose
    private String climate;
    @SerializedName("population")
    @Expose
    private Integer population;
    @SerializedName("quests")
    @Expose
    private List<Quest> quests = new ArrayList<Quest>();

    /**
     * 
     * @return
     *     The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 
     * @return
     *     The name
     */
    public String getName() {
        return name;
    }

    /**
     * 
     * @param name
     *     The name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 
     * @return
     *     The image
     */
    public String getImage() {
        return image;
    }

    /**
     * 
     * @param image
     *     The image
     */
    public void setImage(String image) {
        this.image = image;
    }

    /**
     * 
     * @return
     *     The climate
     */
    public String getClimate() {
        return climate;
    }

    /**
     * 
     * @param climate
     *     The climate
     */
    public void setClimate(String climate) {
        this.climate = climate;
    }

    /**
     * 
     * @return
     *     The population
     */
    public Integer getPopulation() {
        return population;
    }

    /**
     * 
     * @param population
     *     The population
     */
    public void setPopulation(Integer population) {
        this.population = population;
    }

    /**
     * 
     * @return
     *     The quests
     */
    public List<Quest> getQuests() {
        return quests;
    }

    /**
     * 
     * @param quests
     *     The quests
     */
    public void setQuests(List<Quest> quests) {
        this.quests = quests;
    }

}
