# GAでの遺伝子について
ゲーム内でのアクションを遺伝子にする時に2ビットで表したらいけるんじゃないかなと思います
00 := 何もしない
01 := 右に進む
10 := 左に進む
11 := ジャンプ
といった感じで
ゲームの方で読み込む時は遺伝子を2ビットづつ読み込む
とりあえずこんな感じでやって見ます

0 := 右に進む<br>
1 := 左に進む<br>
2 := ジャンプ<br>
に変更。
