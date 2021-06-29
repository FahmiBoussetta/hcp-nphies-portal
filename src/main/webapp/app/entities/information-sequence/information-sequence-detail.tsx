import React, { useEffect } from 'react';
import { Link, RouteComponentProps } from 'react-router-dom';
import { Button, Row, Col } from 'reactstrap';
import { Translate } from 'react-jhipster';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';

import { getEntity } from './information-sequence.reducer';
import { APP_DATE_FORMAT, APP_LOCAL_DATE_FORMAT } from 'app/config/constants';
import { useAppDispatch, useAppSelector } from 'app/config/store';

export const InformationSequenceDetail = (props: RouteComponentProps<{ id: string }>) => {
  const dispatch = useAppDispatch();

  useEffect(() => {
    dispatch(getEntity(props.match.params.id));
  }, []);

  const informationSequenceEntity = useAppSelector(state => state.informationSequence.entity);
  return (
    <Row>
      <Col md="8">
        <h2 data-cy="informationSequenceDetailsHeading">
          <Translate contentKey="hcpNphiesPortalApp.informationSequence.detail.title">InformationSequence</Translate>
        </h2>
        <dl className="jh-entity-details">
          <dt>
            <span id="id">
              <Translate contentKey="global.field.id">ID</Translate>
            </span>
          </dt>
          <dd>{informationSequenceEntity.id}</dd>
          <dt>
            <span id="infSeq">
              <Translate contentKey="hcpNphiesPortalApp.informationSequence.infSeq">Inf Seq</Translate>
            </span>
          </dt>
          <dd>{informationSequenceEntity.infSeq}</dd>
          <dt>
            <Translate contentKey="hcpNphiesPortalApp.informationSequence.item">Item</Translate>
          </dt>
          <dd>{informationSequenceEntity.item ? informationSequenceEntity.item.id : ''}</dd>
        </dl>
        <Button tag={Link} to="/information-sequence" replace color="info" data-cy="entityDetailsBackButton">
          <FontAwesomeIcon icon="arrow-left" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.back">Back</Translate>
          </span>
        </Button>
        &nbsp;
        <Button tag={Link} to={`/information-sequence/${informationSequenceEntity.id}/edit`} replace color="primary">
          <FontAwesomeIcon icon="pencil-alt" />{' '}
          <span className="d-none d-md-inline">
            <Translate contentKey="entity.action.edit">Edit</Translate>
          </span>
        </Button>
      </Col>
    </Row>
  );
};

export default InformationSequenceDetail;
