import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate, TextFormat } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './supporting-info.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const SupportingInfoDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const supportingInfoEntity = useAppSelector(state => state.supportingInfo.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="supportingInfoDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.supportingInfo.detail.title">SupportingInfo</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.id}</dd>
          <dt>
            <span id="sequence">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.sequence">Sequence</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.sequence}</dd>
          <dt>
            <span id="codeLOINC">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.codeLOINC">Code LOINC</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.codeLOINC}</dd>
          <dt>
            <span id="category">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.category">Category</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.category}</dd>
          <dt>
            <span id="codeVisit">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.codeVisit">Code Visit</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.codeVisit}</dd>
          <dt>
            <span id="codeFdiOral">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.codeFdiOral">Code Fdi Oral</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.codeFdiOral}</dd>
          <dt>
            <span id="timing">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.timing">Timing</Translate>
            </span>
          </dt>
          <dd>
            {supportingInfoEntity.timing ? <TextFormat value={supportingInfoEntity.timing} type="date" format={APP_DATE_FORMAT} /> : null}
          </dd>
          <dt>
            <span id="timingEnd">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.timingEnd">Timing End</Translate>
            </span>
          </dt>
          <dd>
            {supportingInfoEntity.timingEnd ? (
              <TextFormat value={supportingInfoEntity.timingEnd} type="date" format={APP_DATE_FORMAT} />
            ) : null}
          </dd>
          <dt>
            <span id="valueBoolean">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueBoolean">Value Boolean</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.valueBoolean ? 'true' : 'false'}</dd>
          <dt>
            <span id="valueString">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueString">Value String</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.valueString}</dd>
          <dt>
            <span id="reason">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.reason">Reason</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.reason}</dd>
          <dt>
            <span id="reasonMissingTooth">
              <Translate contentKey="hcpNphiesPortalApp.supportingInfo.reasonMissingTooth">Reason Missing Tooth</Translate>
            </span>
          </dt>
          <dd>{supportingInfoEntity.reasonMissingTooth}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueQuantity">Value Quantity</Translate>
          </dt>
          <dd>{supportingInfoEntity.valueQuantity ? supportingInfoEntity.valueQuantity.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueAttachment">Value Attachment</Translate>
          </dt>
          <dd>{supportingInfoEntity.valueAttachment ? supportingInfoEntity.valueAttachment.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.supportingInfo.valueReference">Value Reference</Translate>
          </dt>
          <dd>{supportingInfoEntity.valueReference ? supportingInfoEntity.valueReference.id : ''}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.supportingInfo.claim">Claim</Translate>
          </dt>
          <dd>{supportingInfoEntity.claim ? supportingInfoEntity.claim.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/supporting-info" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/supporting-info/${supportingInfoEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default SupportingInfoDetail;
