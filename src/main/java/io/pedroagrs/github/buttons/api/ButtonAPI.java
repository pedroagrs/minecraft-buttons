package io.pedroagrs.github.buttons.api;

import io.pedroagrs.github.buttons.cache.response.ButtonResponse;
import io.pedroagrs.github.buttons.model.Button;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * The API for the plugin
 */
public interface ButtonAPI {

    /**
     * Creates a button and shows it to all online players
     * @param button The button to be created
     * @return The response of the operation
     */
    ButtonResponse create(Button button);

    /**
     * Creates a button and render it to players in a radius
     * @param button The button to be created
     * @param radius The radius to render the button
     * @return The response of the operation
     */
    ButtonResponse create(Button button, int radius);

    /**
     * Creates a button and shows it to viewers
     * @param button The button to be created
     * @param viewers The viewers to see the button
     * @return The response of the operation
     */
    ButtonResponse create(Button button, Player... viewers);

    /**
     * Creates a button and shows it to viewers
     * @param id The id of the button
     * @param text The text of the button
     * @param location The bukkit location of the button
     * @param viewers The viewers to see the button
     * @return The response of the operation
     */
    ButtonResponse create(String id, String text, Location location, Player... viewers);

    /**
     * Delete completely a button
     * @param id The id of the button
     * @return The response of the operation
     */
    ButtonResponse delete(String id);

    /**
     * Update a button
     * @param button The button to be updated - the id must be the same
     * @return The response of the operation
     */
    ButtonResponse update(Button button);
}
