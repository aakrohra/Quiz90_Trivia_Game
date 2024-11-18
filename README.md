# Quiz90 (Trivia Application)



**Domain Idea:** Trivia quiz playability, creation, and shareability
- A trivia quiz game where users can play pre-built quizzes or create and share their own trivia quizzes 
with a scoring system.

**Key Features:**
- Users can create quizzes with multiple-choice questions.
- Players can take quizzes and see their scores.
- Option to share quizzes via unique keys.
- Players can view their statistics/scores
- They can play multiplayer on the same machine. Two people play on the same laptop, taking turns answering questions
- Retrieve trivia questions from an external API (OpenTDB).
- Allow users to create their trivia questions and answers.
- Generate unique keys for user-created questions to enable sharing.
- Store user accounts and trivia questions persistently using the Grade API.
- Allow users to input keys to access shared trivia questions.
- Track user scores and progress in the game.

**Grade API:** [documentation](https://www.postman.com/cloudy-astronaut-813156/csc207-grade-apis-demo/documentation/fg3zkjm/5-password-protected-user).
- Services Provided: User account management and storage for user-generated questions.
- Usage: This will be used to store player data and persistent trivia questions.

**Open TriviaDB:** [documentation](https://opentdb.com/api_config.php).
- Services Provided: Fetch trivia questions based on category, difficulty, and type.
- Usage: Will be used to retrieve a set of trivia questions for the game.






## Entities  

  - Question  
    - Instance Variables:    
        - String questionText  
        - List<String> answerOptions  
        - String correctAnswer  
        - String category  
        - String difficultyLevel


  - Player  
    - Instance Variables:  
      - String username  
      - String password  
      - List<String> createdQuestionsKeys  
      - int currentScore  
        - set to 0 every new session  
      - int highScore
  

- GameSession
  - Instance Variables:  
    - Quiz currentQuiz  
    - Question currentQuestion  
    - Queue whosTurnIsIt  
      - created after every question is asked  
    - Player lastPlayed  
      - determined by who last answered or default (first question) is player 1  
    - List <Player  players


  - Quiz  
    - Instance Variables:
      - List<Question> setOfQuestions  
      - String uniqueKey (for user-generated questions)


## Team Members

Liam Huynh liam.huynh  
Aakaash Rohra aakrohra  
Aref Malekanian Aref-Mal  
Albert Jun Albert-Jun  
Tin Chak Justin Yuen palteofdough  
