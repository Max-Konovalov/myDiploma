package ru.mkonovalov.jurdoc.core.router;

public interface Router {
    String API = "/api";
    String API_V1 = API + "/v1";

    interface Authentication {
        String NAME = "/auth";
        String ROOT = Router.API_V1 + NAME;

        interface Sign {
            interface In {
                String NAME = "/signin";
                String ROOT = Authentication.ROOT + NAME;
            }
        }
    }

    interface User {
        String NAME = "/user";
        String ROOT = Router.API_V1 + NAME;

        interface Roles {
            String NAME = "/roles";
            String ROOT = User.ROOT + NAME;
        }

        interface Id {
            String NAME = "/{id}";
            String ROOT = User.ROOT + NAME;
        }
    }

    interface Management {
        String NAME = "/management";
        String ROOT = Router.API_V1 + NAME;

        interface Document {
            String NAME = "/document";
            String ROOT = Management.ROOT + NAME;

            interface Id {
                String NAME = "/{id}";
                String ROOT = Document.ROOT + NAME;
            }

            interface Package {
                String NAME = "/package";
                String ROOT = Document.ROOT + NAME;

                interface Id {
                    String NAME = "/{id}";
                    String ROOT = Package.ROOT + NAME;
                }
            }
        }
    }

    interface Document {
        String NAME = "/document";
        String ROOT = Router.API_V1 + NAME;

        interface Id {
            String NAME = "/{id}";
            String ROOT = Document.ROOT + NAME;
        }

        interface My {
            String NAME = "/my";
            String ROOT = Document.ROOT + NAME;
        }
    }

    interface Attachment {
        String NAME = "/attachment";
        String ROOT = API_V1 + NAME;

        interface Id {
            String NAME = "/{id:.+}";
            String ROOT = Attachment.ROOT + NAME;
        }
    }

}