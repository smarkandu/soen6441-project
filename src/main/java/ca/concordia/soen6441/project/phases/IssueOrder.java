package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.context.GameEngine;
import ca.concordia.soen6441.project.gameplay.orders.Advance;
import ca.concordia.soen6441.project.gameplay.orders.Deploy;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.log.LogWriter;
import ca.concordia.soen6441.project.gameplay.orders.Diplomacy;
import ca.concordia.soen6441.project.gameplay.PlayerImpl;

/**
 * The IssueOrder class represents the phase where players issue their orders.
 * This includes deploying troops and progressing to the next phase.
 */
public class IssueOrder extends MainPlay {
    private int d_currentPlayIndex;
    private LogWriter d_logWriter;

    /**
     * Constructs an IssueOrder phase.
     *
     * @param p_gameEngine      The game engine instance controlling the game state.
     * @param p_currentPlayIndex The index of the current player issuing orders.
     */
    public IssueOrder(GameContext p_gameEngine, int p_currentPlayIndex) {
        super(p_gameEngine);
        d_currentPlayIndex = p_currentPlayIndex;
        d_logWriter = new LogWriter(LogEntryBuffer.getInstance());
    }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void gamePlayerAdd(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Invalid command for this phase.
     */
    @Override
    public void gamePlayerRemove(String p_playerName) { printInvalidCommandMessage(); }

    /**
     * Allows the current player to deploy troops to a country they own.
     *
     * @param p_countryID The ID of the country to deploy troops to.
     * @param p_toDeploy  The number of troops to deploy.
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        Country l_country = d_gameEngine.getCountryManager().getCountries().get(p_countryID);
        Player l_player = d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);
        int l_numberOfTroopsLeftToDeploy = l_player.getReinforcements() - l_player.getNumberOfTroopsOrderedToDeploy();

        LogEntryBuffer.getInstance().appendToBuffer(l_player.getName() + " issued order to deploy " + p_toDeploy
                + " to " + p_countryID, false);

        if (!l_player.equals(l_country.getOwner())) {
            String l_message = "Player " + l_player.getName() + " doesn't own this country!";
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: " + l_message, true);
        }
        else if (l_numberOfTroopsLeftToDeploy < p_toDeploy)
        {
            String l_message = "Only " + l_numberOfTroopsLeftToDeploy + " left to deploy!";
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: " + l_message, true);
        }
        else
        {
            l_player.issue_order(new Deploy(l_player, l_country, p_toDeploy));
            LogEntryBuffer.getInstance().appendToBuffer(l_player.getName() +
                    " issued order to deploy to " + p_countryID + " granted", false);
        }
    }

    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        Country l_countryFrom = d_gameEngine.getCountryManager().getCountries().get(p_countryNameFrom);
        Country l_countryTo = d_gameEngine.getCountryManager().getCountries().get(p_countryNameTo);

        LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to advance "
                        + p_toAdvance + " from " + p_countryNameFrom + " to " + p_countryNameTo, false);

        if (getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) > 0) // Can only do after all troops are deployed
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You still have " + getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) + " left to deploy!", true);
        }
        else if (!getCurrentPlayer().equals(l_countryFrom.getOwner())) // Player must own origin country
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() + " doesn't own origin country!", true);
        }
        else if (p_toAdvance > getNumberOfTroopsLeftToAdvance(getCurrentPlayer(), l_countryFrom)) // Player have sufficient troops available to advance
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Only " + getNumberOfTroopsLeftToAdvance(getCurrentPlayer(), l_countryFrom) + " left to advance!", true);
        }
        else if (!l_countryFrom.getNeighborIDs().contains(l_countryTo.getID())) // Destination country must be a neighbor to origin country
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: " + l_countryTo.getID() + "is not a neighbor of " + l_countryFrom.getID() + "!", true);
        }
        else
        {
            getCurrentPlayer().issue_order(new Advance(l_countryFrom, l_countryTo, p_toAdvance, getCurrentPlayer(), d_gameEngine));
            LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to advance "
                    + p_toAdvance + " from " + p_countryNameFrom + " to " + p_countryNameTo +  " granted", false);
        }
    }

    @Override
    public void bomb(String p_countryID) {
        // TODO #67
        if (getCurrentPlayer().getHandOfCardsManager().getBombCardManager().hasCard())
        {

        }
    }

    @Override
    public void blockade(String p_countryID) {
        // TODO #68
        if (getCurrentPlayer().getHandOfCardsManager().getBlockadeCardManager().hasCard())
        {

        }
    }

    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        // TODO #69
        if (getCurrentPlayer().getHandOfCardsManager().getAirLiftCardManager().hasCard())
        {

        }
    }


    /**
     * Processes a "negotiate" command issued by a player.
     * <p>
     * Steps:
     * 1. Resets all players' diplomacy lists if populated.
     * 2. Validates that the target player exists and the initiating player has a diplomacy card.
     * 3. If valid, issues a Diplomacy order and logs the result.
     *
     * @param p_playerID The ID of the player to negotiate with.
     */
    @Override
    public void negotiate(String p_playerID) {
        // Step 1: Reset diplomacy for all players at the beginning of the phase (if applicable)
        for (Player l_player : d_gameEngine.getPlayerManager().getPlayers().values()) {
            if (!((PlayerImpl) l_player).getNegotiatedPlayers().isEmpty()) {
                ((PlayerImpl) l_player).resetNegotiatedPlayers();
                LogEntryBuffer.getInstance().appendToBuffer("Diplomacy list reset for player: " + l_player.getName(), true);
            }
        }

        // Step 2: Fetch current player
        Player l_currentPlayer = d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);

        // Step 3: Ensure all reinforcements are deployed before diplomacy
        if (l_currentPlayer.getReinforcements() > 0) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You must deploy all your armies before using a Diplomacy card.", true);
            return;
        }

        // Step 4: Validate the target player
        Player l_targetPlayer = d_gameEngine.getPlayerManager().getPlayers().get(p_playerID);

        if (l_targetPlayer == null) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player with ID '" + p_playerID + "' does not exist.", true);
            return;
        }

        // Step 5: Check if diplomacy card is available (new API)
        if (l_currentPlayer.getHandOfCardsManager().getDiplomacyCardManager().size() == 0) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You don't have a diplomacy card!", true);
            return;
        }

        // Step 6: Issue Diplomacy order
        l_currentPlayer.issue_order(new Diplomacy(l_currentPlayer, l_targetPlayer));
        LogEntryBuffer.getInstance().appendToBuffer("Diplomacy order issued to negotiate with " + l_targetPlayer.getName(), false);
    }



    /**
     * Moves to the next phase in the game.
     * If all players have issued orders, proceeds to order execution.
     */
    @Override
    public void next() {
        if (getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) > 0)
        {
            System.out.println("You still have " + getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) + " left to deploy!");
        }
        else if (d_currentPlayIndex == d_gameEngine.getPlayerManager().getPlayers().size() - 1) {
            OrderExecution l_orderExecution = new OrderExecution(d_gameEngine);
            l_orderExecution.execute();
        } else {
            d_gameEngine.setPhase(new IssueOrder(d_gameEngine, ++d_currentPlayIndex));
        }
    }

    /**
     * Returns the name of the current phase along with the current player's orders.
     *
     * @return A string representing the current phase and the player's orders.
     */
    @Override
    public String getPhaseName() {
        Player l_currentPlayer = d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);
        String l_currentOrders = l_currentPlayer.getOrders().toString();
        String l_currentCards = l_currentPlayer.getHandOfCardsManager().toString();
        return l_currentOrders + "\n" + l_currentCards + "\n" + getClass().getSimpleName() + " ["
                + l_currentPlayer.getName() + "]";
    }

    private Player getCurrentPlayer()
    {
        return d_gameEngine.getPlayerManager().getPlayer(d_currentPlayIndex);
    }

    private int getNumberOfTroopsLeftToDeploy(Player p_player)
    {
        return p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();
    }

    private int getNumberOfTroopsLeftToAdvance(Player p_player, Country p_countryFrom)
    {
        return p_countryFrom.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(p_countryFrom);
    }
}