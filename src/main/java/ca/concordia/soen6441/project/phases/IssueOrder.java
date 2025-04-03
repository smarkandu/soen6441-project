package ca.concordia.soen6441.project.phases;

import ca.concordia.soen6441.project.GameDriver;
import ca.concordia.soen6441.project.gameplay.orders.*;
import ca.concordia.soen6441.project.interfaces.Country;
import ca.concordia.soen6441.project.interfaces.Player;
import ca.concordia.soen6441.project.interfaces.context.GameContext;
import ca.concordia.soen6441.project.log.LogEntryBuffer;
import ca.concordia.soen6441.project.log.LogWriter;

import java.io.Serializable;

/**
 * The IssueOrder class represents the phase where players issue their orders.
 * This includes deploying troops and progressing to the next phase.
 */
public class IssueOrder extends MainPlay implements Serializable {
    private LogWriter d_logWriter;

    /**
     * Constructs an IssueOrder phase.
     *
     * @param p_gameEngine      The game engine instance controlling the game state.
     * @param p_currentPlayIndex The index of the current player issuing orders.
     */
    public IssueOrder(GameContext p_gameEngine, int p_currentPlayIndex) {
        
        p_gameEngine.getPlayerManager().setCurrentPlayerIndex(p_currentPlayIndex);
        d_logWriter = new LogWriter(LogEntryBuffer.getInstance());
    }

    /**
     * Allows the current player to deploy troops to a country they own.
     *
     * @param p_countryID The ID of the country to deploy troops to.
     * @param p_toDeploy  The number of troops to deploy.
     */
    @Override
    public void deploy(String p_countryID, int p_toDeploy) {
        Country l_country = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_countryID);
        Player l_player = GameDriver.getGameEngine().getPlayerManager().getPlayer(GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex());
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
            l_player.addToOrders(new Deploy(l_player, l_country, p_toDeploy));
            LogEntryBuffer.getInstance().appendToBuffer(l_player.getName() +
                    " issued order to deploy to " + p_countryID + " granted", false);
        }
    }

    @Override
    public void advance(String p_countryNameFrom, String p_countryNameTo, int p_toAdvance) {
        Country l_countryFrom = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_countryNameFrom);
        Country l_countryTo = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_countryNameTo);

        LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to advance "
                        + p_toAdvance + " from " + p_countryNameFrom + " to " + p_countryNameTo, false);

        if (l_countryFrom.equals(l_countryTo))
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Source and target territories cannot be the same.", true);
        }
        else if (getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) > 0) // Can only do after all troops are deployed
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
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: " + l_countryTo.getID() + " is not a neighbor of " + l_countryFrom.getID() + "!", true);
        }
        else
        {
            getCurrentPlayer().addToOrders(new Advance(l_countryFrom, l_countryTo, p_toAdvance, getCurrentPlayer()));
            LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to advance "
                    + p_toAdvance + " from " + p_countryNameFrom + " to " + p_countryNameTo +  " granted", false);
        }
    }

    @Override
    public void bomb(String p_countryID) {
        Country l_countryToBomb = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_countryID);
        String l_playerName = getCurrentPlayer().getName();

        LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to bomb "
                + p_countryID , false);

        if (getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) > 0) // Can only do after all troops are deployed
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You still have " + getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) + " left to deploy!", true);
        }
        else if (l_countryToBomb == null)
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() + " target a country who doesn't exist", true);
        }
        else if (!getCurrentPlayer().getHandOfCardsManager().getBombCardManager().hasCard())
        {
            // The player must have a bomb card
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() + " doesn't have a bomb card", true);
        }
        else if (getCurrentPlayer().getOwnedCountries().contains(p_countryID))
        {
            // The player should not own the country
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() + " owns " + p_countryID, true);
        }
        else if (!isTerritoryAdjacent(l_playerName, l_countryToBomb))
        {
            // The enemy's country should be adjacent to one of the player countries
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: The country " + p_countryID + " is not adjacent to any player " + getCurrentPlayer().getName() + " countries", true);
        }
        else if (l_countryToBomb.getTroops() == 0)
        {
            // The player tries to bomb a territory that contains any army troop
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() + " try to bomb an country that contains any army troops", true);
        }
        else
        {
            getCurrentPlayer().addToOrders(new Bomb(getCurrentPlayer(), l_countryToBomb));
            getCurrentPlayer().getHandOfCardsManager().getBombCardManager().removeCard();
            LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to bomb "
                    + p_countryID + " granted", true);
        }
    }

    @Override
    public void blockade(String p_countryID) {
        Country l_country = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_countryID);

        if (getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) > 0) // Can only do after all troops are deployed
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You still have " + getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) + " left to deploy!", true);
        }
        else if (!getCurrentPlayer().equals(l_country.getOwner())) // Player must initially own country to initiate blockade
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() + " doesn't own country for blockade!", true);
        }
        else
        {
            getCurrentPlayer().addToOrders(new Blockade(l_country, getCurrentPlayer()));
            getCurrentPlayer().getHandOfCardsManager().getBlockadeCardManager().removeCard();
            LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to blockade " +
                    p_countryID +  " granted", false);
        }
    }

    @Override
    public void airlift(String p_sourceCountryID, String p_targetCountryID, int p_numArmies) {
        Country l_sourceCountry = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_sourceCountryID);
        Country l_targetCountry = GameDriver.getGameEngine().getCountryManager().getCountries().get(p_targetCountryID);

        LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to airlift "
                + p_numArmies + " from " + p_sourceCountryID + " to " + p_targetCountryID, false);

        if (l_sourceCountry.equals(l_targetCountry))
        {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Source and target countries cannot be the same.", true);
        }
        else if (getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) > 0) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You still have " +
                    getNumberOfTroopsLeftToDeploy(getCurrentPlayer()) + " left to deploy!", true);
        }
        else if (!getCurrentPlayer().equals(l_sourceCountry.getOwner())) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() +
                    " doesn't own source country for airlift!", true);
        }
        else if (!getCurrentPlayer().equals(l_targetCountry.getOwner())) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player " + getCurrentPlayer().getName() +
                    " doesn't own target country for airlift!", true);
        }
        else if (p_numArmies > getNumberOfTroopsLeftToAdvance(getCurrentPlayer(), l_sourceCountry)) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Only " +
                    getNumberOfTroopsLeftToAdvance(getCurrentPlayer(), l_sourceCountry) + " left to airlift!", true);
        }
        else if (!getCurrentPlayer().getHandOfCardsManager().getAirLiftCardManager().hasCard()) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You do not have an Airlift card!", true);
        }
        else {
            getCurrentPlayer().addToOrders(new Airlift(
                    l_sourceCountry,
                    l_targetCountry,
                    p_numArmies,
                    getCurrentPlayer(),
                    GameDriver.getGameEngine()
            ));
            getCurrentPlayer().getHandOfCardsManager().getAirLiftCardManager().removeCard();

            LogEntryBuffer.getInstance().appendToBuffer(getCurrentPlayer().getName() + " issued order to airlift " +
                    p_numArmies + " from " + p_sourceCountryID + " to " + p_targetCountryID + " granted", false);
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
        // Step 1: Fetch current player
        Player l_currentPlayer = GameDriver.getGameEngine().getPlayerManager().getPlayer(GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex());

        // Step 2: Ensure all reinforcements are deployed before diplomacy
        if (getNumberOfTroopsLeftToDeploy(l_currentPlayer) > 0) {
            LogEntryBuffer.getInstance().appendToBuffer(
                    "ERROR: You still have " + getNumberOfTroopsLeftToDeploy(l_currentPlayer) + " left to deploy before using a Diplomacy card!",
                    true
            );
            return;
        }

        // Step 3: Validate the target player
        Player l_targetPlayer = GameDriver.getGameEngine().getPlayerManager().getPlayers().get(p_playerID);

        if (l_targetPlayer == null) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: Player with ID '" + p_playerID + "' does not exist.", true);
            return;
        }

        // Step 4: Check if diplomacy card is available (new API)
        if (l_currentPlayer.getHandOfCardsManager().getDiplomacyCardManager().size() == 0) {
            LogEntryBuffer.getInstance().appendToBuffer("ERROR: You don't have a diplomacy card!", true);
            return;
        }

        // Step 5: Issue Diplomacy order
        l_currentPlayer.addToOrders(new Diplomacy(l_currentPlayer, l_targetPlayer));
        // Remove the used DiplomacyCard
        l_currentPlayer.getHandOfCardsManager().getDiplomacyCardManager().removeCard();
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
        else if (GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex() == GameDriver.getGameEngine().getPlayerManager().getPlayers().size() - 1) {
            OrderExecution l_orderExecution = new OrderExecution();
            l_orderExecution.execute();
        } else {
            GameDriver.getGameEngine().setPhase(new IssueOrder(GameDriver.getGameEngine(), GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex() + 1));
        }
    }

    /**
     * Returns the name of the current phase along with the current player's orders.
     *
     * @return A string representing the current phase and the player's orders.
     */
    @Override
    public String getPhaseName() {
        Player l_currentPlayer = GameDriver.getGameEngine().getPlayerManager().getPlayer(GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex());
        String l_currentOrders = l_currentPlayer.getOrders().toString();
        String l_currentCards = l_currentPlayer.getHandOfCardsManager().toString();
        String l_playerStr = "[" + l_currentPlayer.getName() + "]";

        return l_playerStr + ": " + l_currentOrders + "\n" +
               l_playerStr + ": " + l_currentCards + "\n" +
               getClass().getSimpleName() + " " + l_playerStr;
    }

    private Player getCurrentPlayer()
    {
        return GameDriver.getGameEngine().getPlayerManager().getPlayer(GameDriver.getGameEngine().getPlayerManager().getCurrentPlayerIndex());
    }

    int getNumberOfTroopsLeftToDeploy(Player p_player)
    {
        return p_player.getReinforcements() - p_player.getNumberOfTroopsOrderedToDeploy();
    }

    int getNumberOfTroopsLeftToAdvance(Player p_player, Country p_countryFrom)
    {
        return p_countryFrom.getTroops() - p_player.getNumberOfTroopsOrderedToAdvance(p_countryFrom);
    }



    /**
     * Verify if one of the neighbors' countries belong to a particular player
     *
     * @param p_player string name of the player
     * @param p_countryToBomb country to be bombed
     * @return boolean true if one of the neighbor's countries to be bombed belongs to the player
     */
    private boolean isTerritoryAdjacent(String p_player, Country p_countryToBomb) {
        for(String l_neighbourCountry : p_countryToBomb.getNeighborIDs()) {
            if(getCurrentPlayer().getOwnedCountries().contains(l_neighbourCountry)) {
                return true;
            }
        }
        return false;
    }
}