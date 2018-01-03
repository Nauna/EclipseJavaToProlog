/*PART 2: A)*/

assignment(1,90).
assignment(2,85).
assignment(3,90).
assignment(4,65).
assignment(5,80).

quiz(1,2).
quiz(2,2).
quiz(3,2).
quiz(4,2).
quiz(5,2).
quiz(6,2).
quiz(7,2).
quiz(8,0).
quiz(9,2).
quiz(10,2).

midMark(90).

examMark(70).

midterm(M):- midMark(X),M is (X*20)/100.

exam(E):- examMark(X),E is (X*50)/100.

/*PART2: B)*/

/*
Sum helpers
*/
sumA(T,S,I) :- I=6,T=S.
sumA(T,S,I) :- I < 6 ,
    assignment(I,X),
     J is I + 1 ,
    Y is S+X,
    sumA(T,Y,J).

sumQ(T,S,I) :- I=11,T=S.
sumQ(T,S,I) :- I < 11 ,
    quiz(I,X),
     J is I + 1 ,
    Y is S+X,
    sumQ(T,Y,J).


/*
Calculations with Predefined Predicates: Main solution to Part2
*/
sumAssignments(A) :- sumA(T,0,1), X is T/5, A is (X*20)/100.

sumQuizzes(Q) :- sumQ(T,0,1), Q is (T*10)/20.

grade(G) :- midterm(A),exam(B),sumAssignments(C),sumQuizzes(D),
    G is A+B+C+D.

letter(L,G) :- let(X,Y,Z), G>=Y,G=<Z,L=X.

let(a_plus,90,100). 
let(a,85,89). 
let(a_minus,80,84).
let(b_plus,77,79). 
let(b,73,76). 
let(b_minus,70,72).
let(c_plus,67,69). 
let(c,63,66). 
let(c_minus,60,62).
let(d_plus,57,59). 
let(d,53,56).
let(d_minus,50,52).
let(f,0,49).
