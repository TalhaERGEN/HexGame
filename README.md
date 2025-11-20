# Merhaba 
Proje, 5 sınıftan oluşmaktadır:

GameModel: Oyun işleyişini ve kazananı kontrol eden sınıftır.<br>
HexBoard board -> Oyun Tahtası tipindeki o anki oluşturulan oyun tahtasını tutan nesnedir.<br>
boolen isRedTurn -> Sıradaki oyuncunun kim olduğunu belirler.<br>
GameModel(int size) -> Seçilen boyutta bir oyun tahtası oluşturur ve oyuna her zaman kırmızı oyuncunun başlamasını sağlar.<br>
isValidMove(double x, double y) -> Eğer geçerli bir hamle yapıldıysa hücreyi o anki oyuncunun rengiyle doldurur ve sıradaki oyuncuya geçer.<br>
isGameEnd -> Oyunun bitip bitmediğini kontrol eder.<br>
definedWinner () -> DFS algoritması kullanarak oyunun kazananını belirler.<br>

<img width="888" height="274" alt="image" src="https://github.com/user-attachments/assets/50e743e9-0458-41a4-ba03-f2d0b837ecc2" />





