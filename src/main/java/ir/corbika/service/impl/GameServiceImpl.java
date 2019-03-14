package ir.corbika.service.impl;

import ir.corbika.model.*;
import ir.corbika.model.dto.RunningGame;
import ir.corbika.model.dto.UserScore;
import ir.corbika.model.dto.Winner;
import ir.corbika.repository.*;
import ir.corbika.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameDefinitionRepository gameDefinitionRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private GameAnticipatorRepository gameAnticipatorRepository;

    @Autowired
    private GamePlayerRepository gamePlayerRepository;


    public GameDefinition findById(Long id) throws AccessDeniedException {
        GameDefinition g = gameDefinitionRepository.findOne(id);
        return g;
    }

    @Override
    public List findAll() throws AccessDeniedException {
        return gameDefinitionRepository.findAll();
    }

    public void saveGameDefinition(GameDefinition gameDefinition) {
        gameDefinitionRepository.saveAndFlush(gameDefinition);

    }

    @Override
    public List<UserScore> getUserScores(String username) {
        return getUserScores(userRepository.findByUsername(username));
    }


    private List<UserScore> getUserScores(User user) {
        List<UserScore> userScores = new ArrayList<>();
        for (GamePlayer gp : user.getPlayedGames()) {
            if (gp.getGame().isFinished()) {
                UserScore tmp = new UserScore();
                tmp.setGameDefinition(gp.getGame().getGameDefinition());
                tmp.setUserFullName(user.getFirstName() + " " + user.getLastName());
                tmp.setRole("player");
                tmp.setScore(gp.getScore());
                userScores.add(tmp);
            }
        }
        for (GameAnticipator ga : user.getAnticipatedGames()) {
            if (ga.getGame().isFinished()) {
                UserScore tmp = new UserScore();
                tmp.setGameDefinition(ga.getGame().getGameDefinition());
                tmp.setUserFullName(user.getFirstName() + " " + user.getLastName());
                tmp.setRole("predictor");
                tmp.setScore(ga.getScore());
                userScores.add(tmp);
            }
        }


        return userScores;
    }

    @Override
    public void finish(Winner winner) {
        Game game = gameRepository.findOne(winner.getGameId());
        if (game == null) {
            throw new RuntimeException("game not found!");
        } else if (!game.isRun()) {
            throw new RuntimeException("game is not running!");
        } else if (game.isFinished()) {
            throw new RuntimeException("game is finished!");
        } else if (!game.isVotingClosed()) {
            throw new RuntimeException("voting has not been closed yet!");
        }
        if (game.getPlayers().size() > 0) {
//            User user = userRepository.findByUsername(winner.getWinner());
            game.getGameDefinition().setAnswer(winner.getWinnerId());
            for (GamePlayer gamePlayer : game.getPlayers()) {
                if (gamePlayer.getUser().getId().compareTo(winner.getWinnerId()) == 0) {
                    gamePlayer.setScore(game.getGameDefinition().getWinnerScore());
                } else {
                    gamePlayer.setScore(0);
                }
            }
        }

        Long anticipateAnswer = game.getGameDefinition().getAnswer();
        if (anticipateAnswer < 1) {
            throw new RuntimeException("answer not set!!");
        }
        for (GameAnticipator gameAnticipator : game.getAnticipators()) {
            if (gameAnticipator.getVote() != null && gameAnticipator.getVote().compareTo(game.getGameDefinition().getAnswer()) == 0) {
                gameAnticipator.setScore(game.getGameDefinition().getAnticipateWinScore());
            } else if (gameAnticipator.getVote() == null) {
                gameAnticipator.setScore(0);

            } else {
                gameAnticipator.setScore(game.getGameDefinition().getAnticipateLoseScore());
            }
        }
        game.setFinished(true);
        gameRepository.saveAndFlush(game);

    }


    @Override
    public void startGame(Long gameId) {
        List run = gameRepository.findAllByRunAndFinished(true, false);
        if (run.size() > 0) {
            throw new RuntimeException("there is a running game!");
        }
        GameDefinition gameDefinition = gameDefinitionRepository.findOne(gameId);
        start(gameDefinition);
    }

    @Override
    public RunningGame getUserGame(String username) {
        List<Game> runnings = gameRepository.findAllByRunAndFinished(true, false);
        if (runnings.size() == 0) {
            throw new RuntimeException("there is no running game!");
        } else if (runnings.size() != 1) {
            throw new RuntimeException("more than one running game!");
        }
        Game candidateGame = runnings.get(0);
        for (GameAnticipator gameUser : candidateGame.getAnticipators()) {
            if (gameUser.getUser().getUsername().compareTo(username) == 0) {
                return new RunningGame(candidateGame.getId(), candidateGame.getGameDefinition());
            }
        }
        throw new RuntimeException("there is no suitable running game!");


    }

    @Override
    public void voteGame(String username, Long gameId, Long choice) {

        User user = userRepository.findByUsername(username);
        GameAnticipator ga = gameAnticipatorRepository.findOne(new GameUserId(gameId, user.getId()));
        if (ga == null || ga.getGame() == null) {
            throw new RuntimeException("game not found!");
        } else if (!ga.getGame().isRun()) {
            throw new RuntimeException("game is not running!");
        } else if (ga.getGame().isFinished()) {
            throw new RuntimeException("game is finished!");
        } else if (ga.getGame().isVotingClosed()) {
            throw new RuntimeException("voting time is over!");
        }
        ga.setVote(choice);
        gameAnticipatorRepository.saveAndFlush(ga);

//        Game game = gameRepository.findOne(gameId);
//        List<GameAnticipator> gameAnticipators = game.getAnticipators();
//        GameAnticipator candidateGameUser = null;
//        for (GameAnticipator gameAnticipator:gameAnticipators) {
//            if (gameAnticipator.getUser().getId().compareTo(user.getId())==0){
//                candidateGameUser = gameAnticipator;
//            }
//        }
//        if (candidateGameUser==null){
//            throw new RuntimeException("game not found!");
//        }
//        candidateGameUser.setVote(choice);
//        gameAnticipatorRepository.saveAndFlush(candidateGameUser);


    }

    @Override
    public void closeVoting(Long gameId) {
        Game game = gameRepository.findOne(gameId);
        if (game.isVotingClosed()) {
            throw new RuntimeException("voting already closed!");
        }
        game.setVotingClosed(true);
        gameRepository.saveAndFlush(game);
    }

    @Override
    public List<UserScore> getScoreBoard() {
        List<User> users = userRepository.findAll();
        List<UserScore> usersScore = new ArrayList<>();
        for (User user : users) {
            List<UserScore> scores = getUserScores(user);
            UserScore cumulativeUserScore = new UserScore();
            cumulativeUserScore.setRole("cumulativeUserScore");
            cumulativeUserScore.setUserFullName(user.getFirstName() + " " + user.getLastName());
            if (scores.size() > 0)
                cumulativeUserScore.setScore(scores.stream().map(score -> score.getScore()).reduce(Integer::sum).get());
            else
                cumulativeUserScore.setScore(0);
            usersScore.add(cumulativeUserScore);
        }
        return usersScore;
    }

    @Override
    public List findRunning() {
        List<Game> games = gameRepository.findAll();
        games.forEach(game -> {
            game.setAnticipators(null);
            game.setPlayers(null);
        });
        return games;

    }

    @Transactional
    public void start(GameDefinition gameDefinition) {
        List<User> users = userRepository.findAllByDeterment(false);
        Game game = new Game();
        game.setName(gameDefinition.getName());
        game.setRun(true);
        List<User> players = choosePlayers(gameDefinition.getPlayersCount(), users);
        List<User> anticipators = calculateAnticipator(users, players);

        players.forEach(user -> game.addUserAsPlayer(user));
        anticipators.forEach(user -> game.addUserAsAnticipator(user));
        if (players.size() > 0) {
            gameDefinition.clearChoices();
            players.forEach(player -> gameDefinition.addChoice(new Choice(player.getId(), player.getFirstName() + "  " + player.getLastName())));
        }
        game.setGameDefinition(gameDefinition);
        gameRepository.save(game);


    }

    private List<User> calculateAnticipator(List<User> users, List<User> players) {
        List<User> anticipators = new ArrayList<>();
        for (User user : users) {
            boolean flag = false;
            for (User player : players) {
                if (player.getId().compareTo(user.getId()) == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                anticipators.add(user);
        }
        return anticipators;
    }

    public List<User> choosePlayers(int count, List<User> users) {
        if (count == 0)
            return new ArrayList();
        List<GamePlayer> playedUsers = gamePlayerRepository.findAll();
        int candidateCount = Math.min(count, users.size() - playedUsers.size());
        List<User> notPlayers = new ArrayList<>();
        for (User user : users) {
            boolean flag = false;
            for (GamePlayer gp : playedUsers) {
                if (gp.getUser().getId().compareTo(user.getId()) == 0) {
                    flag = true;
                    break;
                }
            }
            if (!flag)
                notPlayers.add(user);
        }
        Collections.shuffle(notPlayers);
        return notPlayers.subList(0, candidateCount);

    }
//        List<User> notPlayers = users.stream().map(user -> )

}
