; Nome: Gabriel Prost Gomes Pereira

(ns functional.trab10.core)

; 1. Na aula disponível em: https://replit.com/@frankalcantara/ClojureAula2?v=1 foram destacadas diversas funções (expressões), como funções de primeira ordem, disponíveis em Clojure. Sua primeira tarefa será descrever cada uma destas funções e apresentar dois exemplos de uso de cada uma delas. Lembre-se os exemplos precisam ser utilizados de forma que o resutado da função possa ser visto no terminal.

; Funções: assoc dissoc range map inc filter odd into nth conj sort partition-by empty count char

; assoc. recebe um mapa/vetor e retorna ele com chaves e valores adicionais/trocados

(println
 "1-1.assoc"
 ; trocar o valor da chave :b por "oi" e adicionar chave :c com valor 50 
 (assoc {:a 20 :b 30} :b "oi" :c 50))

(println
 "1-2.assoc"
 ; em vetores o indice é a chave (o indice não pode ser maior que o tamanho do vetor, se por exatamente o tamanho então o elemento é concatenado no final)
 (assoc [1 4 "world"] 1 "hello" 3 \!))

; dissoc. recebe um mapa e retira chaves dele

(println
 "1-3.dissoc"
 ; retira a chave :2 do mapa
 (dissoc {:1 "hello" :2 "world" :3 \!} :2))

(println
 "1-4.dissoc"
 ; é possivel passar chaves que não existem no mapa
 (dissoc {:hello 1 :world 2 :! 3} :1 :hello "dissoc"))

; range. produz uma sequencia de numeros preguiçosa
; (range) produz a sequencia [0; infinito[ com step 1
; (range %end) produz a sequencia [0; %end[ com step 1
; (range %start %end) produz a sequencia [%start; %end[ com step 1
; (range %start %end %step) produz a sequencia [%start; %end[ com step %step

(println
 "1-5.range "
 (range 10))

(println
 "1-6.range"
 (range -1 9/5 1/10))

; map. aplica uma função em cada elemento/par de chave/valor de uma coleção

(println
 "1-7.map"
 (map (fn [[_ valor]] (repeat 3 (* 2 valor))) {:a 1 :b 2 :c 3}))

(println
 "1-8.map"
 ; um mapa pode ser dado para trocar chaves na coleção pelo valor, e outros por nil
 (map {3 "hello" 6 "world" 9 \!} (range 10)))

; inc. retorna um numero 1 maior que o numero providenciado

(println
 "1-9.inc"
 (inc 4.5))

(println
 "1-10.inc"
 (map inc [0 -1/2 7.2]))

; filter. recebe uma função que retorna um booleano, e uma coleção, retorna a coleção com todos os elementos que, quando aplicadas na função, retornam true

(println
 "1-11.filter"
 (filter even? (range 10)))

(println
 "1-12.filter"
 (filter (fn [[c _]] (even? c)) {1 "hello" 6 2 10 \!}))

; odd?. recebe um inteiro e retorna true se ele for impar
(println
 "1-13.odd?"
 (odd? 13))

(println
 "1-14.odd?"
 (map odd? (range 10)))

; into. recebe duas coleções, retorna uma coleção do tipo da primeira com os itens da segunda concatenados

(println
 "1-15.into"
 (into [1 2 \-] (range 5)))

(println
 "1-16.into"
 (into {:a 1} '([:b 2] ["hello" "world"])))

; nth. retorna o item de uma sequencia no indice. pode retornar um outro valor providenciado se o indice não existir na sequencia

(println
 "1-17.nth"
 (nth (range 20 200 25) 2))

(println
 "1-18.nth"
 (nth (range 5) 10 "não encontrado"))

; conj. recebe uma coleção e itens, retorna a coleção com os itens adicionados

(println
 "1-19.conj"
 ; o lugar em que os itens são adicionados depende da coleção (lista -> começo, vetor -> fim)
 (conj '("hello" "world") \! 10))

(println
 "1-20.conj"
 ; pode sobrescrever uma chave do mapa
 (conj {:a "hello" :b "world"} [:c \!] {:d 1 :b 2}))

; sort. ordena uma coleção, usa a função compare se uma função de comparação não por providenciada

(println
 "1-21.sort"
 (sort > (range 10)))

(println
 "1-22.sort"
 (sort "Hello World!"))

; partition-by. recebe uma função e uma coleção, particiona a coleção toda vez que a função retorna um valor novo, dado um item da coleção como entrada

(println
 "1-23.partition-by"
 (partition-by #(Character/isLowerCase %) "Hello world!"))

(println
 "1-24.partition-by"
 (partition-by #(<= 5 %) (range 10)))

; empty?. recebe uma coleção e retorna true se estiver vazia

(println
 "1-25.empty?"
 (empty? '(1 2)))

(println
 "1-26.empty?"
 (empty? '()))

; count. recebe uma coleção e retorna quantos elementos ela possui

(println
 "1-27.count"
 (count (range 10)))

(println
 "1-28.count"
 (count "Hello World!"))

; char. tenta converter algo para um caractere. converte numeros usando assci

(println
 "1-29.char"
 (char 65))

(println
 "1-30.char"
 (apply str (map char (range 97 (+ 97 26)))))


; 2. Utilizando a linguagem Clojure, crie uma função chamada ehPrimo que receba um inteiro e devolva True caso este inteiro seja primo e False caso contrário.

(defn fator? [n f]
  (zero? (rem n f)))

; sequencia preguiçosa de todos os primos
(def primos
  ; para cada inteiro n <- [2;inf[
  (for [n (drop 2 (range)) 
        ; pegar todos os primos cujo quadrado é menor ou igual a n
        ; e mante-lo apenas se não possuir um fator nessa lista.
        ; só é possivel porque "for" é preguiçoso
        :when (->> primos
                   (take-while #(<= (* % %) n)) 
                   (not-any? #(fator? n %))) 
        ]
    n))

(defn ehPrimo [n]
  (if (< n 2)
    false
    (loop [p primos]
      (cond
        (< n (#(* % %) (first p))) true
        (fator? n (first p)) false
        :else (recur (rest p))))))

(println
 "2-1"
 (filter ehPrimo (range 100)))

; 3. Utilizando a linguagem Clojure, crie uma função chamada fatoresPrimos que receba um inteiro e devolva uma lista dos seus fatores primos. Decomposição em fatores primos é uma tarefa fundamental da aritmética.

(defn fatoresPrimos [n]
  (if (< n 2)
    []
    (loop [n n fatores [] p primos]
      (if (= 1 n)
        fatores
        (let [new-p (drop-while #(not (fator? n %)) p)]
          (recur (/ n (first new-p)) (conj fatores (first new-p)) new-p))))))

(println
 "3-1"
 (fatoresPrimos 177))

(println
 "3-2"
 (fatoresPrimos 253))

(println
 "3-3"
 (fatoresPrimos 1729))

(println 
 "3-4\n"
 (map #(str % " -> " (fatoresPrimos %) \newline) (range 20)))


; 4. Utilizando a linguagem Clojure, crie uma função chamada todosPrimos que receba dois valores inteiros e devolve todos os números primos que existam entre estes dois valores.

(defn todosPrimos [a b]
  (->> primos
       (drop-while #(> a %))
       (take-while #(> b %))))

(println
 "4-1"
 (todosPrimos 50 100))

(println
 "4-2"
 (todosPrimos 5000 5050))

(println
 "4-3"
 (todosPrimos -10 -5))

(println
 "4-4"
 (todosPrimos 3000 300))