import scala.io.Source

object Similarities {
  val LOWER_BOUND_SIMILARITY = 0.5
  
  def trigrams(word : String) = 
    ("  "+word.trim+" ").sliding(3).toList           
  
  val allWords = (for (word <- Source.fromFile("words").getLines if (!(word contains "'"));
                      trigram <- trigrams(word)) yield trigram -> word).toList  

  val trigToWords = allWords.groupBy(_._1).mapValues(_ map (_._2)) 
                      
  def similarWords(word: String) = {
    val trigs = trigrams(word)

    def allSimilarWords = for (trig <- trigs if (trigToWords contains trig); word <- trigToWords(trig)) yield word
    
    def similarWordsTrigramOccurence = allSimilarWords.foldLeft(Map[String,Int]())((m, word) => m.updated(word, m.getOrElse(word, 0)+1))

    def similarWordsPercentage = similarWordsTrigramOccurence map (tuple => tuple._1 -> (tuple._2.toDouble/trigs.size))
    
    def validWord(tuple : Tuple2[String, Double]) = 
      (tuple._1.length-word.length).abs < 2 && tuple._2 > LOWER_BOUND_SIMILARITY
    
    similarWordsPercentage.filter(validWord).toSeq.sortWith((a,b) => a._2 > b._2)
  }
    
  def main(args : Array[String]) = {
    for ((word, similar) <-similarWords(args(0))) {
      println("%s: %.1f%%".format(word, similar*100))
    }
  }
}