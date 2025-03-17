const {NativeSessionStorageModule, NativeLocalStorageModule} = NativeModules;

const context = (() => {
    if (typeof globalThis !== 'undefined') return globalThis
    if (typeof self !== 'undefined') return self
    if (typeof window !== 'undefined') return window
    if (typeof global !== 'undefined') return global
    return Function('return this')();
})()

const s = Symbol('')

/**
 * 后备 Storage 实现
 */
class BackStorage {
    constructor() {
        this[s] = new Map();
        console.warn('正在使用后备 Storage 实现')
    }

    setItem(key, value) {
        /**
         * @type{Map}
         */
        const m = this[s]
        m.set(key, value);
    }

    getItem(key) {
        /**
         * @type{Map}
         */
        const m = this[s]
        return m.get(key) || null
    }

    hasItem(key) {
        /**
         * @type{Map}
         */
        const m = this[s]
        return m.has(key)
    }

    keys() {
        /**
         * @type{Map}
         */
        const m = this[s]
        return Array.from(m.keys())
    }

    removeItem(key) {
        /**
         * @type{Map}
         */
        const m = this[s]
        m.delete(key)
    }

    clear() {
        /**
         * @type{Map}
         */
        const m = this[s]
        m.clear()
    }
}

/**
 * 代理
 * @type {ProxyHandler<NativeSessionStorageModule | NativeLocalStorageModule | BackStorage | any>}
 */
const handler = {
    get(target, key) {
        if (typeof target[key] === 'function') return target[key].bind(target)
        return target.getItem(key)
    },
    set(target, key, value) {
        if (typeof target[key] === 'function') return false
        target.setItem(key, value)
        return true
    },
    has(target, key) {
        return target.hasItem(key)
    },
    ownKeys(target) {
        return target.keys()
    },
    deleteProperty(target, key) {
        target.removeItem(key)
        return true
    },
}

/**
 * 代理
 * @param target {NativeSessionStorageModule | NativeLocalStorageModule | any}
 * @return {ProxyHandler<NativeSessionStorageModule | NativeLocalStorageModule | any>}
 */
const handlerNative = (target) => {
    const keywords = new Set(['setItem', 'getItem', 'hasItem', 'keys', 'removeItem', 'clear']);

    return ({
        get(t, key) {
            if (keywords.has(key) && typeof target[key] === 'function') return target[key].bind(target)
            return target.getItem(key)
        },
        set(t, key, value) {
            if (keywords.has(key) && typeof target[key] === 'function') return false
            target.setItem(key, value)
            return true
        },
        has(t, key) {
            return target.hasItem(key)
        },
        ownKeys(t) {
            return target.keys()
        },
        deleteProperty(t, key) {
            target.removeItem(key)
            return true
        },
    });
}

let ssProxy;
let lsProxy;
if (context.Storage && context.localStorage instanceof context.Storage && context.sessionStorage instanceof context.Storage) {
    ssProxy = context.sessionStorage;
    lsProxy = context.localStorage;
} else if (NativeLocalStorageModule && NativeSessionStorageModule) {
    ssProxy = new Proxy({}, handlerNative(NativeSessionStorageModule))
    lsProxy = new Proxy({}, handlerNative(NativeLocalStorageModule))
} else {
    ssProxy = new Proxy(new BackStorage(), handler)
    lsProxy = new Proxy(new BackStorage(), handler)
}

export const sessionStorage = ssProxy;
export const localStorage = lsProxy;

