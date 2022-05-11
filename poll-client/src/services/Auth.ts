import {createApi} from "../components/ApiClient";
import Config from "./Config";
import {LoginResponse, PollUser} from "../types/AuthModel";

const AUTH_USER_STORAGE_KEY = '-tr-usr';
const AUTH_TOKEN_STORAGE_KEY = '-tr-token';


class AuthStorage {

  private storage: Storage

  constructor(storage: Storage) {
    this.storage = storage
  }

  clear() {
    this.storage.removeItem(AUTH_TOKEN_STORAGE_KEY)
    this.storage.removeItem(AUTH_USER_STORAGE_KEY)
  }

  isAuthenticated(): boolean {
    return this.storage.getItem(AUTH_TOKEN_STORAGE_KEY) !== null
  }

  setAccessToken(token: string) {
    this.storage.setItem(AUTH_TOKEN_STORAGE_KEY, token);
  }

  getAccessToken(): string | null {
    return this.storage.getItem(AUTH_TOKEN_STORAGE_KEY);
  }

  setUser(user: PollUser) {
    this.storage.setItem(AUTH_USER_STORAGE_KEY, JSON.stringify(user))
  }

  getUser(): PollUser | null {
    let data = this.storage.getItem(AUTH_USER_STORAGE_KEY);
    if (data) {
      try {
        return JSON.parse(data)
      } catch (e) {
      }
    }
    return null
  }
}

export class AuthService {

  static authStorage: AuthStorage = createStorage();

  static api = createApi(Config.AUTH_API_URL)

  static login(usernameOrEmail: string, password: string) {
    this.authStorage = createStorage()
    let form = {usernameOrEmail, password};
    return this.api.post('/api/login', form)
      .then<LoginResponse>(res => res.data)
      .then(data => {
        if (data.success) {
          AuthService.authStorage.setAccessToken(data.accessToken)
          AuthService.authStorage.setUser(data.user)
        }
        return {
          authenticated: data.success,
          user: data.user
        }
      })
  }

  static checkToken(): Promise<boolean> {
    let token = this.getAccessToken();
    if (token != null) {
      let form = new FormData();
      form.append("token", token)
      return this.api.post('/api/token', form)
        .then(res => res.data)
        .then(data => {
          return !!data.success
        })
        .catch(_ => false)
    } else {
      return Promise.resolve(false);
    }
  }

  static checkToken2(): Promise<boolean> {
    let token = this.getAccessToken();
    if (token != null) {
      return Promise.resolve(true);
    } else {
      return Promise.resolve(false);
    }
  }

  static logout() {
    return new Promise<boolean>(resolve => {
      AuthService.authStorage.clear()
      resolve(true)
    })
  }

  static isAuthenticated(): boolean {
    return this.authStorage.isAuthenticated()
  }

  static isAdmin(): boolean {
    return this.getUser()?.type == 'admin'
  }

  static isUser(): boolean {
    return this.getUser()?.type == 'user'
  }

  static isAnonymous(): boolean {
    return this.getUser()?.type == 'anonymous'
  }

  static getUser(): PollUser | null {
    return this.authStorage.getUser()
  }

  static getAccessToken() {
    return this.authStorage.getAccessToken()
  }
}

function createStorage(): AuthStorage {
  return new AuthStorage(localStorage)
}
