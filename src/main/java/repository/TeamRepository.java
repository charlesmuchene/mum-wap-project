package repository;

import model.Team;

import java.util.Collection;

/**
 * Team repository
 */
public class TeamRepository implements Repository {

    /**
     * Get all Teams
     *
     * @return {@link Collection} of {@link Team}
     */
    public Collection<Team> getAllTeams() {
        return retrieveAll(Team.class);
    }

    /**
     * Get team using the given id
     *
     * @param id Id of the team
     * @return {@link Team}
     */
    public Team getTeam(int id) {
        return retrieve(Team.class, id);
    }
}
