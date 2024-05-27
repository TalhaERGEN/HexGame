# Merhaba 
Proje, 5 sınıftan oluşmaktadır:

GameModel: Oyun işleyişini ve kazananı kontrol eden sınıftır.
HexBoard board -> Oyun Tahtası tipindeki o anki oluşturulan oyun tahtasını tutan nesnedir.
boolen isRedTurn -> Sıradaki oyuncunun kim olduğunu belirler.
GameModel(int size) -> Seçilen boyutta bir oyun tahtası oluşturur ve oyuna her zaman kırmızı oyuncunun başlamasını sağlar.
isValidMove(double x, double y) -> Eğer geçerli bir hamle yapıldıysa hücreyi o anki oyuncunun rengiyle doldurur ve sıradaki oyuncuya geçer.
isGameEnd -> Oyunun bitip bitmediğini kontrol eder.
definedWinner () -> DFS algoritması kullanarak oyunun kazananını belirler.

![image](https://github.com/TalhaERGEN/HexGame/assets/65824311/a4d252f6-b5f7-40b7-8690-ca5f53b8b9d1)



