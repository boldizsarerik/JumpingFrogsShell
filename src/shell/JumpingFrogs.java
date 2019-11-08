package shell;

import java.util.ArrayList;
import java.util.List;

public class JumpingFrogs extends Shell
{
     List<WaterLilyStatus> list = new ArrayList<>();
     boolean gameRuns = false;
     int n = 0;
        
    public JumpingFrogs()
    {
       
        addCommand( new Command("new")
        {
            @Override
            public boolean execute( String... args )
            {
                if(gameRuns)
                    return false;
                if(args.length > 1)
                    return false;
                if(args.length == 1 && Double.parseDouble( args[0] ) % 1 != 0)
                    return false;
                if(args.length == 1 && Integer.parseInt( args[0] ) <= 0)
                    return false;
                
                if( args.length == 0 )
                {
                    n = 3;
                    for ( int i = 0; i < 3; i++ )
                        list.add( WaterLilyStatus.TOAD );
                    
                    list.add( WaterLilyStatus.FREE );
                    
                    for ( int i = 0; i < 3; i++ )
                        list.add( WaterLilyStatus.TREE_FROG );
                    
                    gameRuns = true;
                }
                
                if(args.length == 1)
                {
                    n = Integer.parseInt( args[0] );
                    for ( int i = 0; i < n; i++ )
                        list.add( WaterLilyStatus.TOAD );
                    list.add( WaterLilyStatus.FREE );
                    for ( int i = 0; i < n; i++ )
                        list.add( WaterLilyStatus.TREE_FROG );
                    
                    gameRuns = true;
                }
                    
                return true;
            }
        });
        
        addCommand( new Command("X")
        {
            @Override
            public boolean execute( String... args )
            {
                if( !gameRuns )
                    return false;
                
                if( args.length != 1 )
                    return false;
                
                if( args[0].equals("walk"))
                {
                    boolean canWalkAFrog = false;
                    for ( int i = 0; i < list.size()-1; i++ )
                        if(list.get( i ) == WaterLilyStatus.TOAD && list.get( i+1 ) == WaterLilyStatus.FREE)
                        {
                            list.set( i, WaterLilyStatus.FREE );
                            list.set( i+1, WaterLilyStatus.TOAD );
                            canWalkAFrog = true;
                            break;
                        }
                    if(!canWalkAFrog)
                        return false;
                }
                
                if(args[0].equals( "jump" ))
                {
                    boolean canJumpAFrog = false;
                    for ( int i = 0; i < list.size()-2; i++ )
                        if(list.get( i ) == WaterLilyStatus.TOAD && list.get( i+1 ) == WaterLilyStatus.TREE_FROG
                                && list.get( i+2 ) == WaterLilyStatus.FREE)
                        {
                            list.set( i, WaterLilyStatus.FREE );
                            list.set( i+2, WaterLilyStatus.TOAD);
                            canJumpAFrog = true;
                            break;
                        }
                    if(!canJumpAFrog)
                        return false;
                }
                return true;
            }
        });
        
        addCommand( new Command("O")
        {
            @Override
            public boolean execute( String... args )
            {
                if( !gameRuns )
                    return false;
                
                if( args.length != 1 )
                    return false;
                
                if( args[0].equals("walk"))
                {
                    boolean canWalkAFrog = false;
                    for ( int i = list.size()-1; i > 0; --i )
                    {
                        if(list.get( i ) == WaterLilyStatus.TREE_FROG && list.get( i-1 ) == WaterLilyStatus.FREE)
                        {
                            list.set( i, WaterLilyStatus.FREE );
                            list.set( i-1, WaterLilyStatus.TREE_FROG);
                            canWalkAFrog = true;
                            break;
                        }
                    }
                    if(!canWalkAFrog)
                        return false;
                }
                
                if(args[0].equals( "jump" ))
                {
                    boolean canJumpAFrog = false;
                    for ( int i = list.size()-1; i > 1 ; --i )
                    {
                        if(list.get( i ) == WaterLilyStatus.TREE_FROG
                                && list.get( i-1 ) == WaterLilyStatus.TOAD
                                && list.get( i-2 ) == WaterLilyStatus.FREE)
                        {
                            list.set( i, WaterLilyStatus.FREE );
                            list.set( i-2, WaterLilyStatus.TREE_FROG);
                            canJumpAFrog = true;
                            break;
                        }
                    }
                    if(!canJumpAFrog)
                        return false;
                }
                
                return true;
            }
        });
        
        addCommand( new Command("print")
        {
            @Override
            public boolean execute( String... args )
            {
                if( !gameRuns )
                    return false;
                if(args.length != 0)
                    return false;
                if(!gameRuns)
                    return false;
                
                System.out.println( "Az aktuális helyzet:" );
                for ( int i = 0; i < list.size()-1; i++ )
                    System.out.printf( "%s ", list.get( i ));
                System.out.println( list.get( list.size()- 1 ) );
                
                boolean allTreeFrogsinRightPlace = true;
                for ( int i = 0; i < n; i++ )
                    if(list.get( i ) != WaterLilyStatus.TREE_FROG)
                        allTreeFrogsinRightPlace = false;
                
                boolean freeWaterLilyinCenter = true;
                if( list.get( n ) != WaterLilyStatus.FREE )
                    freeWaterLilyinCenter = false;
                
                boolean allToadsinRightPlace = true;
                for ( int i = list.size()-1; i > n+1; --i )
                    if(list.get( i ) != WaterLilyStatus.TOAD)
                        allToadsinRightPlace = false;
                
                if(allToadsinRightPlace && freeWaterLilyinCenter && allTreeFrogsinRightPlace)
                {
                    System.out.println( "A játék véget ért! Minden béka a helyére került!" );
                    gameRuns = false;
                    if(!gameRuns)
                    list.removeAll( list );
                    return true;
                }
                
                boolean canWalkAToad = false;
                    for ( int i = 0; i < list.size()-1; i++ )
                        if(list.get( i ) == WaterLilyStatus.TOAD && list.get( i+1 ) == WaterLilyStatus.FREE)
                        {
                            canWalkAToad = true;
                            break;
                        }
                
                boolean canJumpAToad = false;
                    for ( int i = 0; i < list.size()-2; i++ )
                        if(list.get( i ) == WaterLilyStatus.TOAD && list.get( i+1 ) == WaterLilyStatus.TREE_FROG
                                && list.get( i+2 ) == WaterLilyStatus.FREE)
                        {
                            canJumpAToad = true;
                            break;
                        }
                
                    boolean canWalkATreeFrog = false;
                    for ( int i = list.size()-1; i > 0; --i )
                    {
                        if(list.get( i ) == WaterLilyStatus.TREE_FROG && list.get( i-1 ) == WaterLilyStatus.FREE)
                        {
                            canWalkATreeFrog = true;
                            break;
                        }
                    }
                    
                    boolean canJumpATreeFrog = false;
                    for ( int i = list.size()-1; i > 1 ; --i )
                    {
                        if(list.get( i ) == WaterLilyStatus.TREE_FROG
                                && list.get( i-1 ) == WaterLilyStatus.TOAD
                                && list.get( i-2 ) == WaterLilyStatus.FREE)
                        {
                            canJumpATreeFrog = true;
                            break;
                        }
                    }
                
                if(!canJumpAToad && !canJumpATreeFrog && !canWalkAToad && ! canWalkATreeFrog)
                {
                    System.out.println( "Holthelyzet! A játék véget ért!" );
                    gameRuns = false;
                }
                       
                if(!gameRuns)
                    list.removeAll( list );
                return true;
            }
        });
        
    }
}
