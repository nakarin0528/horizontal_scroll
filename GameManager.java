public class GameManager{
    
    private GameMode _gameState = null;
    public GameMode State(){return _gameState;}
    
    public GameManager(Game game){
        init();
    }
    
    public void init(){
        _gameState = new TitleState();
    }
    
    public void ChangeMode(GameMode mode){
        _gameState = mode;
    }
    
    public void GameMainUpdate(){
        _gameState.run(this);
    }
}
