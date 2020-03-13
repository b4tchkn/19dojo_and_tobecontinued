# Dojo Android 2019

## 課題 - プロフィール交換アプリ

自身のプロフィールをQRコードを使って交換するアプリを作成します。
プロフィールに含む情報は以下のとおりです。

1. フルネーム `e.g. Keita Kagurazaka`
2. GitHubアカウント `e.g. k-kagurazaka`
3. Twitterアカウント `e.g. kkagurazaka`

QRコードの内部形式は以下のフォーマットによるURLとします。

```
[Scheme] ca-tech
[Host] dojo
[Path] /share
[QueryParameter]
 - iam: <url encoded fullname>
 - tw: <twitter account>
 - gh: <github account>
 
e.g. ca-tech://dojo/share?iam=Keita%20Kagurazaka&tw=kkagurazaka&gh=k-kagurazaka
```

プロフィールを送信する側がQRコードを生成し、受け取る側がそのQRコードを読み取ることでプロフィールを交換します。
QRコードの生成はライブラリを用いてアプリ内に組み込みます。
QRコードの読み取りにはQRコードリーダーアプリを利用し、アプリはURLから起動できるものとします。

交換したプロフィール情報および自身のプロフィール情報は端末内に永続化します。
永続化の形式は問いませんが、交換したプロフィール情報はSQLiteデータベース、自身のプロフィール情報はSQLiteデータベースまたはSharedPreferencesの利用を推奨します。

### 画面構成

#### プロフィール登録画面

自身のプロフィールを入力、編集する画面です。

必須なのはGitHubアカウントのみで、他の情報は記入されていなくても保存・QRコード生成ができるものとします。
アプリを起動するたびに自身のプロフィールを入力するのは使い勝手が悪いので、何らかの手法で永続化するものとします。

#### QRコード表示画面

自身のプロフィールの交換用QRコードを表示する画面です。

QRコードの内部形式であるURLは前述のフォーマットに従うものとします。
また、QRコード画像の生成には3rd partyライブラリである [ZXing Android Embedded](https://github.com/journeyapps/zxing-android-embedded) を利用します。

#### プロフィール一覧画面

交換したプロフィールの一覧を表示する画面です。

一覧の表示形式は縦型のスクロール可能なリスト形式とし、RecyclerViewwの利用を推奨します。
表示順は自由とします。登録順でもいいですし、名前のアルファベット順でもよいです。

一覧の各セルには以下の情報を表示するものとします。

- フルネーム
- GitHubブランドロゴとアカウント名
- Twitterブランドロゴとアカウント名

また、各SNSのロゴまたはアカウント名をタップしたときに各アカウントのプロフィールページをアプリ内ブラウザから閲覧できるものとします。
各SNSのプロフィールページのURL形式は以下のとおりです。

```
Twitter -> https://twitter.com/{account_name}
GitHub -> https://github.com/{account_name}
```

アプリ内ブラウザには、WebView (もしくはChrome Custome Tabs) を利用するものとします。

### 補足事項

QRコードまわりの動作確認には下記ツールの利用を推奨します。
標準規格に準拠していれば他のツールでもおそらく問題ありませんが、少なくとも下記ツールは動作確認がとれています。

- QRコード生成
  - https://webqr.com
  - https://github.com/mdp/qrterminal
- QR読み取りアプリ
  - https://play.google.com/store/apps/details?id=com.google.zxing.client.android

