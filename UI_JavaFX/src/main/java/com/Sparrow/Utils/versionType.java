package com.Sparrow.Utils;

public enum versionType {
    RELEASE{
        @Override
        public String toString(){
            return "Release";
        }
    },
    SNAPSHOT{
        @Override
        public String toString(){
            return "Snapshot";
        }
    },
    OLD_ALPHA{
        @Override
        public String toString(){
            return "Old_Alpha";
        }
    },
    OLD_BETA{
        @Override
        public String toString(){
            return "Old_Beta";
        }
    }
}