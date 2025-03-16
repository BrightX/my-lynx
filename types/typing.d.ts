declare let NativeModules: {
    NativeLocalStorageModule: {
        setItem(key: string, value: string): void;
        getItem(key: string): string | null;
        hasItem(key: string): boolean;
        keys(): string[] | ArrayLike<string | symbol>;
        removeItem(key: string): void;
        clear(): void;
        [key: string]: string | null | void | any;
    };
    NativeSessionStorageModule: {
        setItem(key: string, value: string): void;
        getItem(key: string): string | null;
        hasItem(key: string): boolean;
        keys(): string[] |  ArrayLike<string | symbol>;
        removeItem(key: string): void;
        clear(): void;
        [key: string]: string | null | void | any;
    };
};
