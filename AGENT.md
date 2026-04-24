# Agent Context — aionios-service

## What this app is

Backend API for Aionios, a Web3 time capsule platform. Handles authentication, capsule metadata persistence, and Web3/IPFS integration.

## Stack

Java 17, Spring Boot 3.2, Spring Security, Spring Data JPA, Web3j, IPFS Java API, Maven

## Project structure

- `src/main/java/io/` — main package root
- Standard Spring Boot layout: controllers, services, repositories, entities, DTOs, config

## Key patterns

- REST API consumed by aionios-ui frontend
- Spring Security for JWT/auth
- Web3j for Ethereum contract interaction
- IPFS for decentralized content storage
- JPA entities mapped to relational DB

## Focus for this agent

This is a Java backend — the agent should look for:

- Controller endpoints that are defined but return stub/empty responses
- Service methods that are declared but not implemented (throw NotImplementedException or return null)
- Missing request validation on DTOs
- Endpoints referenced by the frontend that don't exist yet
