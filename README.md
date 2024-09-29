
```
api-server
├─ .git
│  ├─ COMMIT_EDITMSG
│  ├─ config
│  ├─ description
│  ├─ FETCH_HEAD
│  ├─ HEAD
│  ├─ hooks
│  │  ├─ applypatch-msg.sample
│  │  ├─ commit-msg.sample
│  │  ├─ fsmonitor-watchman.sample
│  │  ├─ post-update.sample
│  │  ├─ pre-applypatch.sample
│  │  ├─ pre-commit.sample
│  │  ├─ pre-merge-commit.sample
│  │  ├─ pre-push.sample
│  │  ├─ pre-rebase.sample
│  │  ├─ pre-receive.sample
│  │  ├─ prepare-commit-msg.sample
│  │  ├─ push-to-checkout.sample
│  │  ├─ sendemail-validate.sample
│  │  └─ update.sample
│  ├─ index
│  ├─ info
│  │  └─ exclude
│  ├─ logs
│  │  ├─ HEAD
│  │  └─ refs
│  │     ├─ heads
│  │     │  └─ master
│  │     └─ remotes
│  │        └─ origin
│  │           └─ master
├─ .gitignore
├─ gradle
│  └─ wrapper
│     └─ gradle-wrapper.properties
├─ gradlew
├─ gradlew.bat
└─ src
   ├─ main
   │  ├─ java
   │  │  └─ com
   │  │     ├─ api
   │  │     │  ├─ ApiServerApplication.java
   │  │     │  ├─ controller
   │  │     │  │  ├─ cm
   │  │     │  │  └─ ur
   │  │     │  │     └─ UserController.java
   │  │     │  ├─ dto
   │  │     │  │  └─ UserDTO.java
   │  │     │  ├─ entity
   │  │     │  │  └─ User.java
   │  │     │  ├─ repository
   │  │     │  │  └─ UserRepository.java
   │  │     │  └─ service
   │  │     │     └─ UserService.java
   │  │     └─ huntstore
   │  └─ resources
   │     └─ application.yml
   └─ test
      └─ java
         ├─ com
         │  ├─ api
         │  │  └─ ApiServerApplicationTests.java
         │  └─ huntstore
         └─ controller
            └─ TestController.java

```