import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './coverage-eligibility-response.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const CoverageEligibilityResponseDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const coverageEligibilityResponseEntity = useAppSelector(state => state.coverageEligibilityResponse.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="coverageEligibilityResponseDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.detail.title">CoverageEligibilityResponse</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.id}</dd>
          <dt>
            <span id="value">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.value">Value</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.value}</dd>
          <dt>
            <span id="system">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.system">System</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.system}</dd>
          <dt>
            <span id="parsed">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.parsed">Parsed</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.parsed}</dd>
          <dt>
            <span id="outcome">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.outcome">Outcome</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.outcome}</dd>
          <dt>
            <span id="serviced">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.serviced">Serviced</Translate>
            </span>
          </dt>
          <dd>
            {coverageEligibilityResponseEntity.serviced ? (
              <TextFormat value={coverageEligibilityResponseEntity.serviced} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="servicedEnd">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.servicedEnd">Serviced End</Translate>
            </span>
          </dt>
          <dd>
            {coverageEligibilityResponseEntity.servicedEnd ? (
              <TextFormat value={coverageEligibilityResponseEntity.servicedEnd} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="disposition">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.disposition">Disposition</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.disposition}</dd>
          <dt>
            <span id="notInforceReason">
              <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.notInforceReason">Not Inforce Reason</Translate>
            </span>
          </dt>
          <dd>{coverageEligibilityResponseEntity.notInforceReason}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.patient">Patient</Translate>
          </dt>
          <dd>{coverageEligibilityResponseEntity.patient ? coverageEligibilityResponseEntity.patient.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.coverageEligibilityResponse.insurer">Insurer</Translate>
          </dt>
          <dd>{coverageEligibilityResponseEntity.insurer ? coverageEligibilityResponseEntity.insurer.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/coverage-eligibility-response" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/coverage-eligibility-response/${coverageEligibilityResponseEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default CoverageEligibilityResponseDetail;
