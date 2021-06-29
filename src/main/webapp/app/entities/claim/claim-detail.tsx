import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './claim.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const ClaimDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const claimEntity = useAppSelector(state => state.claim.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="claimDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.claim.detail.title">Claim</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{claimEntity.id}</dd>
          <dt>
            <span id="guid">
              <Translate contentKey="hcpNphiesPortalApp.claim.guid">Guid</Translate>
            </span>
          </dt>
          <dd>{claimEntity.guid}</dd>
          <dt>
            <span id="isQueued">
              <Translate contentKey="hcpNphiesPortalApp.claim.isQueued">Is Queued</Translate>
            </span>
          </dt>
          <dd>{claimEntity.isQueued ? 'true' : 'false'}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.claim.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{claimEntity.parsed}</dd>
          <dt>
            <span id="identifier">
              <Translate contentKey="hcpNphiesPortalApp.claim.identifier">Identifier</Translate>
            </span>
          </dt>
          <dd>{claimEntity.identifier}</dd>
          <dt>
            <span id="use">
              <Translate contentKey="hcpNphiesPortalApp.claim.use">Use</Translate>
            </span>
          </dt>
          <dd>{claimEntity.use}</dd>
          <dt>
            <span id="type">
              <Translate contentKey="hcpNphiesPortalApp.claim.type">Type</Translate>
            </span>
          </dt>
          <dd>{claimEntity.type}</dd>
          <dt>
            <span id="subType">
              <Translate contentKey="hcpNphiesPortalApp.claim.subType">Sub Type</Translate>
            </span>
          </dt>
          <dd>{claimEntity.subType}</dd>
          <dt>
            <span id="eligibilityOffline">
              <Translate contentKey="hcpNphiesPortalApp.claim.eligibilityOffline">Eligibility Offline</Translate>
            </span>
          </dt>
          <dd>{claimEntity.eligibilityOffline}</dd>
          <dt>
            <span id="eligibilityOfflineDate">
              <Translate contentKey="hcpNphiesPortalApp.claim.eligibilityOfflineDate">Eligibility Offline Date</Translate>
            </span>
          </dt>
          <dd>
            {claimEntity.eligibilityOfflineDate ? (
              <TextFormat value={claimEntity.eligibilityOfflineDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="authorizationOfflineDate">
              <Translate contentKey="hcpNphiesPortalApp.claim.authorizationOfflineDate">Authorization Offline Date</Translate>
            </span>
          </dt>
          <dd>
            {claimEntity.authorizationOfflineDate ? (
              <TextFormat value={claimEntity.authorizationOfflineDate} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="billableStart">
              <Translate contentKey="hcpNphiesPortalApp.claim.billableStart">Billable Start</Translate>
            </span>
          </dt>
          <dd>
            {claimEntity.billableStart ? <TextFormat value={claimEntity.billableStart} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="billableEnd">
              <Translate contentKey="hcpNphiesPortalApp.claim.billableEnd">Billable End</Translate>
            </span>
          </dt>
          <dd>{claimEntity.billableEnd ? <TextFormat value={claimEntity.billableEnd} type="date" format={APP_DATE_FORMAT} /> : null}</dd>
          <dt>
            <span id="priority">
              <Translate contentKey="hcpNphiesPortalApp.claim.priority">Priority</Translate>
            </span>
          </dt>
          <dd>{claimEntity.priority}</dd>
          <dt>
            <span id="fundsReserve">
              <Translate contentKey="hcpNphiesPortalApp.claim.fundsReserve">Funds Reserve</Translate>
            </span>
          </dt>
          <dd>{claimEntity.fundsReserve}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.encounter">Encounter</Translate>
          </dt>
          <dd>{claimEntity.encounter ? claimEntity.encounter.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.eligibilityResponse">Eligibility Response</Translate>
          </dt>
          <dd>{claimEntity.eligibilityResponse ? claimEntity.eligibilityResponse.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.patient">Patient</Translate>
          </dt>
          <dd>{claimEntity.patient ? claimEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.provider">Provider</Translate>
          </dt>
          <dd>{claimEntity.provider ? claimEntity.provider.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.insurer">Insurer</Translate>
          </dt>
          <dd>{claimEntity.insurer ? claimEntity.insurer.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.prescription">Prescription</Translate>
          </dt>
          <dd>{claimEntity.prescription ? claimEntity.prescription.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.originalPrescription">Original Prescription</Translate>
          </dt>
          <dd>{claimEntity.originalPrescription ? claimEntity.originalPrescription.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.payee">Payee</Translate>
          </dt>
          <dd>{claimEntity.payee ? claimEntity.payee.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.referral">Referral</Translate>
          </dt>
          <dd>{claimEntity.referral ? claimEntity.referral.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.facility">Facility</Translate>
          </dt>
          <dd>{claimEntity.facility ? claimEntity.facility.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.claim.accident">Accident</Translate>
          </dt>
          <dd>{claimEntity.accident ? claimEntity.accident.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/claim" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/claim/${claimEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default ClaimDetail;
