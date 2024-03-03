import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;

class Question {
    private String question;
    private List<String> options;
    private int correctOptionIndex;

    public Question(String question, List<String> options, int correctOptionIndex) {
        this.question = question;
        this.options = options;
        this.correctOptionIndex = correctOptionIndex;
    }

    public String getQuestion() {
        return question;
    }

    public List<String> getOptions() {
        return options;
    }

    public int getCorrectOptionIndex() {
        return correctOptionIndex;
    }
}

public class Quiz {
    private List<Question> questions;
    private int currentQuestionIndex;
    private int score;
    private Timer timer;

    public Quiz(List<Question> questions) {
        this.questions = questions;
        this.currentQuestionIndex = 0;
        this.score = 0;
        this.timer = new Timer();
    }

    public void startQuiz() {
        askQuestion();
    }

    private void askQuestion() {
        if (currentQuestionIndex < questions.size()) {
            Question currentQuestion = questions.get(currentQuestionIndex);
            System.out.println("Question: " + currentQuestion.getQuestion());
            List<String> options = currentQuestion.getOptions();
            for (int i = 0; i < options.size(); i++) {
                System.out.println((i + 1) + ". " + options.get(i));
            }
            startTimer();
            getUserAnswer();
        } else {
            showResult();
        }
    }

    private void startTimer() {
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("\nTime's up!");
                checkAnswer(-1); // -1 indicates no answer
            }
        }, 10000); // 10 seconds timer
    }

    private void getUserAnswer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter your choice (1-" + questions.get(currentQuestionIndex).getOptions().size() + "): ");
        int choice = scanner.nextInt();
        checkAnswer(choice - 1); // Subtract 1 to convert to zero-based index
    }

    private void checkAnswer(int userChoice) {
        timer.cancel(); // Cancel timer
        Question currentQuestion = questions.get(currentQuestionIndex);
        if (userChoice == currentQuestion.getCorrectOptionIndex()) {
            System.out.println("Correct!");
            score++;
        } else if (userChoice == -1) {
            System.out.println("No answer submitted. Moving to the next question.");
        } else {
            System.out.println("Incorrect. The correct answer is: " +
                    currentQuestion.getOptions().get(currentQuestion.getCorrectOptionIndex()));
        }
        currentQuestionIndex++;
        askQuestion();
    }

    private void showResult() {
        System.out.println("\nQuiz ended!");
        System.out.println("Your score: " + score + "/" + questions.size());
    }

    public static void main(String[] args) {
        List<Question> questions = new ArrayList<>();
        // Add questions with options and correct answer index
        questions.add(new Question("What is the capital of France?",
                List.of("London", "Paris", "Berlin", "Madrid"), 1));
        questions.add(new Question("What is the largest planet in our solar system?",
                List.of("Earth", "Venus", "Jupiter", "Saturn"), 2));
        questions.add(new Question("Who wrote 'To Kill a Mockingbird'?",
                List.of("Ernest Hemingway", "Harper Lee", "J.K. Rowling", "Charles Dickens"), 1));

        Quiz quiz = new Quiz(questions);
        quiz.startQuiz();
    }
}
