similar-words
=============

Google provides suggestions when I misspell a word. Wondering how I can do something similar with
only a little effort I stumbled upon the trigram method in PostgreSQL: http://www.postgresql.org/docs/9.1/static/pgtrgm.html.

>A trigram is a group of three consecutive characters taken from a string. 
>We can measure the similarity of two strings by counting the number of trigrams they share. 
>This simple idea turns out to be very effective for measuring the similarity of words in many natural languages.

Using the words file that is provided with each linux distribution I replicated this functionality partailly.
Calling the similarWords method will return a list of tuples with similar words and a percentage indicating
the similarity. 

The dictionary is parsed only once, so when using it consecutively it will perform better then with just one looup.