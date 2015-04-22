Face Unlock Demo

This project demonstrates how to use Face Unlock in your own apps.  It will only work on phones that have Face Unlock installed, configured, and enabled - that is, on barely any devices in the wild.  Despite that, it's pretty cool.

Use `LockUtils.hasFaceUnlock(Context)` to determine if the device meets the above criteria, and use `FaceUnlock` to activate Face Unlock itself.
