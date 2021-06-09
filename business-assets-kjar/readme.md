Rules Assets project (KJAR)
=============================

This project contains a simple set of rules (DRL) and a DMN model to demonstrate the basics of Red Hat Decision Manager.

 * project structure generated using `kie-kjar-archetype`:

```
mvn archetype:generate \
  -DarchetypeGroupId=org.kie \
  -DarchetypeArtifactId=kie-kjar-archetype \
  -DarchetypeVersion=7.54.0.Final \
  -DgroupId=com.redhat.demos \
  -DartifactId=business-assets-kjar \
  -Dversion=1.0.0-SNAPSHOT
```

 * enriched with some additional assets based on [this notes/gist](https://gist.github.com/rafaeltuelho/2a0f17ece526389b36096e9d89a16623)

## Testing the use cases

### running the Unit Tests
You can run from your preferred IDE or directly from maven

```
mvn test
```

### Deploying on a Standard Kie Server
You can choose to deploy your `kjar` on kie-server running on SpringBoot or JBoss EAP runtime.
You can also import this project into your Decision Central Authoring environment and deploy from there.

#### Use the standard kie-server Rest API

##### Drools Rules

 * on EAP (Business/Decision Central)
```
POST http://localhost:8080/kie-server/services/rest/server/containers/instances/business-assets-kjar
```

 * on SpringBoot based kie-server
```
POST http://localhost:8080/rest/server/containers/instances/business-assets-kjar
```

request body
```json
{
    "lookup": "stateless-session",
    "commands": [
        {
            "insert": {
                "object": {
                    "Person": {
                        "name": "Rafael",
                        "age": 37,
                        "city": "Burlington",
                        "state": "MA"
                    }
                },
                "out-identifier": "person",
                "return-object": true
            }
        },
        {
            "fire-all-rules": {
                "max": 10,
                "out-identifier": "fired rules"
            }
        },
        {
            "get-objects": {
                "out-identifier": "objects"
            }
        }        
    ]
}
```

response
```json
{
    "type": "SUCCESS",
    "msg": "Container business-assets-kjar successfully called.",
    "result": {
        "execution-results": {
            "results": [
                {
                    "value": 2,
                    "key": "fired rules"
                },
                {
                    "value": {
                        "com.redhat.demos.model.Person": {
                            "name": "Rafael",
                            "age": 30,
                            "state": "MA",
                            "city": "Burlington"
                        }
                    },
                    "key": "person"
                },
                {
                    "value": [
                        {
                            "com.redhat.demos.model.Person": {
                                "name": "Rafael",
                                "age": 30,
                                "state": "MA",
                                "city": "Burlington"
                            }
                        },
                        "Hello from rules engine!",
                        {
                            "com.redhat.demos.model.DecisionResponse": {
                                "adult": true
                            }
                        }
                    ],
                    "key": "objects"
                }
            ],
            "facts": [
                {
                    "value": {
                        "org.drools.core.common.DefaultFactHandle": {
                            "external-form": "0:4:1174646672:1174646672:4:DEFAULT:NON_TRAIT:com.redhat.demos.model.Person"
                        }
                    },
                    "key": "person"
                }
            ]
        }
    }
}
```

##### DMN Model

 * on EAP (Business/Decision Central)
```
POST http://localhost:8080/kie-server/services/rest/server/containers/business-assets-kjar/dmn
```

 * on SpringBoot based kie-server
```
POST http://localhost:8080/rest/server/containers/business-assets-kjar/dmn
```

request body
```json
{
    "model-namespace": "https://kiegroup.org/dmn/_BD61D32F-0F8E-40F3-AFF2-25065DFC7437",
    "model-name": "adult",
    "decision-name": [],
    "dmn-context": {
        "Person": {
            "name": "Rafael",
            "age": 30,
            "city": "Burlington",
            "state": "MA"
        }
    }
}
```

response
```json
{
    "type": "SUCCESS",
    "msg": "OK from container 'business-assets-kjar'",
    "result": {
        "dmn-evaluation-result": {
            "messages": [],
            "model-namespace": "https://kiegroup.org/dmn/_BD61D32F-0F8E-40F3-AFF2-25065DFC7437",
            "model-name": "adult",
            "decision-name": [],
            "dmn-context": {
                "is an Adult?": true,
                "Person": {
                    "city": "Burlington",
                    "name": "Rafael",
                    "state": "MA",
                    "age": 30
                }
            },
            "decision-results": {
                "_2418CF23-E20A-4599-BED8-A48E50DA493B": {
                    "messages": [],
                    "decision-id": "_2418CF23-E20A-4599-BED8-A48E50DA493B",
                    "decision-name": "is an Adult?",
                    "result": true,
                    "status": "SUCCEEDED"
                }
            }
        }
    }
}
```